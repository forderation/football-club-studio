package com.forderation.footballclubstudio.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.forderation.footballclubstudio.BuildConfig
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.presenter.DetailLeaguePresenter
import com.forderation.footballclubstudio.activity.view.DetailLeagueView
import com.forderation.footballclubstudio.adapter.ClubAdapter
import com.forderation.footballclubstudio.adapter.EventAdapter
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.fragment.UnderLeagueFragment
import com.forderation.footballclubstudio.fragment.ResultEventFragment
import com.forderation.footballclubstudio.model.club.Club
import com.forderation.footballclubstudio.model.league.League
import com.forderation.footballclubstudio.utils.BottomSheet
import com.forderation.footballclubstudio.utils.EventViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_league_detail.*
import kotlinx.android.synthetic.main.toolbar_league.*

class LeagueDetailActivity : AppCompatActivity(), DetailLeagueView {

    override fun showBottomDesc(desc: String) {
        BottomSheet(desc).show(supportFragmentManager, "BottomSheet")
    }

    companion object {
        const val LEAGUE_INTENT = "LEAGUE_INTENT"
    }

    override fun showListClub(clubList: List<Club>) {
        adapter.clubList = clubList.toMutableList()
        mEventAdapter = EventAdapter(arrayListOf()){ e, h, a ->
            val intent = Intent(this,EventDetailActivity::class.java)
            intent.putExtra(EventDetailActivity.ID_EVENT,e.idEvent)
            intent.putExtra(EventDetailActivity.HOME_BADGE_URL,h)
            intent.putExtra(EventDetailActivity.AWAY_BADGE_URL,a)
            startActivity(intent)
        }
        showUnderLeagueFg()
    }

    private fun showUnderLeagueFg(){
        val fg = UnderLeagueFragment.newInstance(
            league?.id!!,
            league?.name!!.replace(" ","%20")
        )
        val fragment = fragmentManager.findFragmentByTag(UnderLeagueFragment::class.java.simpleName)
        if (fragment !is UnderLeagueFragment){
            fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, fg,UnderLeagueFragment::class.java.simpleName)
                .commit()
        }
        searchView.visibility = View.VISIBLE
    }

    private fun showFgEventSearchResult(){
        val fg = ResultEventFragment()
        fg.adapter = mEventAdapter
        val fragment = fragmentManager.findFragmentByTag(ResultEventFragment::class.java.simpleName)
        if (fragment !is ResultEventFragment){
            fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, fg,ResultEventFragment::class.java.simpleName)
                .commit()
        }
    }

    private lateinit var fragmentManager:FragmentManager
    private lateinit var adapter: ClubAdapter
    private lateinit var presenter: DetailLeaguePresenter
    private var league: League? = null

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        presenter.getClubList(league?.name!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_detail)
        fragmentManager = supportFragmentManager
        adapter = ClubAdapter { }
        presenter = DetailLeaguePresenter(this, Gson(), ApiClient())
        league = intent.getParcelableExtra(LEAGUE_INTENT)
        presenter.getClubList(league?.name!!)
        Picasso.get().load(league?.getSmallTrophy()).fit().centerInside().into(trophy_league)
        Picasso.get().load(league?.badge).fit().centerInside().into(league_badge)
        desc_league.text = league?.description
        supportActionBar?.title = league?.name
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        snackbar = Snackbar.make(coordinator_layout,getString(R.string.loading_search),Snackbar.LENGTH_INDEFINITE)
        desc_league.setOnClickListener { showBottomDesc(desc_league.text.toString())}
        viewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        viewModel.listEvent().observe(this, Observer {
            if(it!=null){
                val listEvent = it.filter { e ->
                    e.strSport.equals(BuildConfig.TYPE_SPORT) && e.idLeague.equals(league?.id)
                }.toMutableList()
                mEventAdapter.setEventList(listEvent)
            }
        })
        viewModel.onResponse().observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })
        viewModel.onLoading().observe(this, Observer {
            if(it){
                snackbar.show()
            }else{
                snackbar.dismiss()
            }
        })
    }

    private lateinit var snackbar: Snackbar

    private lateinit var mEventAdapter: EventAdapter

    private lateinit var viewModel:EventViewModel
    private lateinit var searchView: SearchView

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        inflater.inflate(R.menu.option_menu, menu)
        val searchItem = menu?.findItem(R.id.search_menu)
        searchView = searchItem?.actionView as SearchView
        searchView.visibility = View.INVISIBLE
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                showUnderLeagueFg()
                return true
            }
        })
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint) + " ${league?.name}"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getData(query)
                }
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                true
            }
            R.id.search_menu -> {
                showFgEventSearchResult()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}

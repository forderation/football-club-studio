package com.forderation.footballclubstudio.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.forderation.footballclubstudio.BuildConfig
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.view.DetailLeagueView
import com.forderation.footballclubstudio.adapter.ClubAdapter
import com.forderation.footballclubstudio.adapter.EventAdapter
import com.forderation.footballclubstudio.fragment.ResultSearchUnderLeague
import com.forderation.footballclubstudio.fragment.UnderLeagueFragment
import com.forderation.footballclubstudio.model.league.League
import com.forderation.footballclubstudio.utils.BottomSheet
import com.forderation.footballclubstudio.utils.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_league_detail.*
import kotlinx.android.synthetic.main.toolbar_league.*

class LeagueDetailActivity : AppCompatActivity(), DetailLeagueView {

    companion object {
        const val LEAGUE_INTENT = "LEAGUE_INTENT"
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
    }

    private fun showFgSearchResult(){
        if(currentState==null) return
        val fg = ResultSearchUnderLeague.newInstance(currentState?:"")
        fg.eventAdapter = mSearchEvent
        fg.teamAdapter = mSearchClub
        val fragment = fragmentManager.findFragmentByTag(ResultSearchUnderLeague::class.java.simpleName)
        if (fragment !is ResultSearchUnderLeague){
            fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, fg,ResultSearchUnderLeague::class.java.simpleName)
                .commit()
        }
    }

    private lateinit var fragmentManager:FragmentManager
    private var league: League? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_detail)
        fragmentManager = supportFragmentManager
        league = intent.getParcelableExtra(LEAGUE_INTENT)
        Picasso.get().load(league?.getSmallTrophy()).fit().centerInside().into(trophy_league)
        Picasso.get().load(league?.badge).fit().centerInside().into(league_badge)
        desc_league.text = league?.description
        supportActionBar?.title = league?.name
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        snackbar = Snackbar.make(coordinator_layout,getString(R.string.loading_search),Snackbar.LENGTH_INDEFINITE)
        desc_league.setOnClickListener {
            BottomSheet(desc_league.text.toString()).show(supportFragmentManager, "BottomSheet")
        }
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.listEvent().observe(this, Observer {
            if(it!=null){
                val listEvent = it.filter { e ->
                    e.strSport.equals(BuildConfig.TYPE_SPORT) && e.idLeague.equals(league?.id)
                }.toMutableList()
                mSearchEvent.setEventList(listEvent)
            }
        })
        viewModel.listTeam().observe(this, Observer {
            if(it!=null){
                val listClub = it.filter { e -> e.idLeague.equals(league?.id) }.toMutableList()
                mSearchClub.clubList = listClub
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
        mSearchEvent = EventAdapter(arrayListOf()){ e, h, a ->
            val intent = Intent(this,EventDetailActivity::class.java)
            intent.putExtra(EventDetailActivity.ID_EVENT,e.idEvent)
            intent.putExtra(EventDetailActivity.HOME_BADGE_URL,h)
            intent.putExtra(EventDetailActivity.AWAY_BADGE_URL,a)
            startActivity(intent)
        }
        mSearchClub = ClubAdapter{}
        showUnderLeagueFg()
    }

    private lateinit var snackbar: Snackbar

    private lateinit var mSearchEvent: EventAdapter
    private lateinit var mSearchClub: ClubAdapter

    private lateinit var viewModel:SearchViewModel
    private lateinit var searchView: SearchView
    private var searchItem: MenuItem? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        inflater.inflate(R.menu.option_menu, menu)
        searchItem = menu?.findItem(R.id.search_menu)
        searchView = searchItem?.actionView as SearchView
        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                showUnderLeagueFg()
                return true
            }
        })
        searchItem?.isVisible = false
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                true
            }
            R.id.search_menu -> {
                showFgSearchResult()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun setVisibilitySearch(isShow: Boolean) {
        searchItem?.isVisible = isShow
    }


    private var currentState: String? = null

    override fun setCurrentStateSearch(state: String) {
        currentState = state
        when(state){
            UnderLeagueFragment.STATE_LIST_EVENT -> {
                searchView.queryHint = resources.getString(R.string.search_event_hint) + " ${league?.name}"
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            viewModel.getListNextMatch(query)
                        }
                        return false
                    }
                })
            }
            UnderLeagueFragment.STATE_LIST_TEAM -> {
                searchView.queryHint = resources.getString(R.string.search_team_hint) + " ${league?.name}"
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            viewModel.getListClub(query)
                        }
                        return false
                    }
                })
            }
        }
    }
}

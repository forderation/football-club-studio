package com.forderation.footballclubstudio.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.presenter.DetailLeaguePresenter
import com.forderation.footballclubstudio.activity.view.DetailLeagueView
import com.forderation.footballclubstudio.adapter.ClubAdapter
import com.forderation.footballclubstudio.adapter.PagerAdapter
import com.forderation.footballclubstudio.fragment.EventFragment
import com.forderation.footballclubstudio.model.club.Club
import com.forderation.footballclubstudio.model.league.League
import com.forderation.footballclubstudio.utils.BottomSheet
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_league_detail.*
import kotlinx.android.synthetic.main.toolbar_league.*

class LeagueDetailActivity : AppCompatActivity(), DetailLeagueView{

    override fun showBottomDesc(desc: String) {
        BottomSheet(desc).show(supportFragmentManager,"BottomSheet")
    }

    companion object {
        const val ADDITIONAL_INFO = "ADDITIONAL_INFO"
        fun launchActivity(context: Context, league: League){
            val intent = Intent(context,LeagueDetailActivity::class.java)
            intent.putExtra(ADDITIONAL_INFO,league)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun showListClub(clubList: List<Club>) {
        adapter.clubList = clubList.toMutableList()
        val latestMatch = EventFragment(league?.id!!,clubList)
        val pagerAdapter = PagerAdapter(supportFragmentManager)
        pagerAdapter.listFragment.add(latestMatch)
        view_pager_event.adapter = pagerAdapter
        view_pager_event.adapter?.notifyDataSetChanged()
    }

    private lateinit var adapter: ClubAdapter
    private lateinit var presenter: DetailLeaguePresenter
    private lateinit var rvClub: RecyclerView
    private var league: League? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_detail)
        adapter = ClubAdapter { }
        rvClub = list_club
        presenter = DetailLeaguePresenter(this)
        rvClub.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvClub.adapter = adapter
        league = intent.getParcelableExtra(ADDITIONAL_INFO)
        presenter.getClubList(league?.name!!)
        Picasso.get().load(league?.getSmallTrophy()).fit().centerInside().into(trophy_league)
        desc_league.text = league?.description
        supportActionBar?.title = league?.name
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        desc_league.setOnClickListener { showBottomDesc(desc_league.text.toString()) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                true
            }
            R.id.search_menu -> {
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}

package com.forderation.footballclubstudio.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.presenter.DetailLeaguePresenter
import com.forderation.footballclubstudio.activity.view.DetailLeagueView
import com.forderation.footballclubstudio.adapter.ClubAdapter
import com.forderation.footballclubstudio.model.club.Club
import kotlinx.android.synthetic.main.toolbar_league.*

class LeagueDetailActivity : AppCompatActivity(),DetailLeagueView {

    override fun showListClub(clubList: List<Club>) {
        adapter.clubList = clubList.toMutableList()
    }

    private lateinit var adapter: ClubAdapter
    private lateinit var presenter: DetailLeaguePresenter
    private lateinit var rvClub: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_detail)
        adapter = ClubAdapter {  }
        rvClub = list_club
        presenter = DetailLeaguePresenter(this)
        rvClub.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvClub.adapter = adapter
        presenter.getClubList("English Premier League")
    }
}

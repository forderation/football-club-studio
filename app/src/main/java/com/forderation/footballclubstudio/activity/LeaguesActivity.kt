package com.forderation.footballclubstudio.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.presenter.LeaguesPresenter
import com.forderation.footballclubstudio.activity.view.LeaguesView
import com.forderation.footballclubstudio.adapter.LeagueAdapter
import com.forderation.footballclubstudio.model.league.League
import com.fxn.OnBubbleClickListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_leagues.*
import org.jetbrains.anko.support.v4.onRefresh

class LeaguesActivity : AppCompatActivity(), LeaguesView {

    override fun addLeague(item: League) {
        adapter.addItem(item)
    }

    private lateinit var snackBar: Snackbar
    private lateinit var presenter: LeaguesPresenter
    private lateinit var adapter: LeagueAdapter

    override fun showLoading() {
        snackBar.show()
        progress_loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        snackBar.dismiss()
        progress_loading.visibility = View.INVISIBLE
        swipe_layout.isRefreshing = false
    }

    override fun loadFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leagues)
        adapter = LeagueAdapter(arrayListOf(), this) {
            val intent = Intent(applicationContext, LeagueDetailActivity::class.java)
            intent.putExtra(LeagueDetailActivity.LEAGUE_INTENT, it)
            startActivity(intent)
        }
        snackBar =
            Snackbar.make(swipe_layout, "Now run to get league ...", Snackbar.LENGTH_INDEFINITE)
        presenter = LeaguesPresenter(this, this)
        rv_leagues.layoutManager = GridLayoutManager(this, 2)
        rv_leagues.adapter = adapter
        swipe_layout.onRefresh {
            adapter.clearAdapter()
            presenter.getLeagues()
        }

        tab_item_menu.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                if(currentItemId != null && id == currentItemId) return
                adapter.clearAdapter()
                when(id){
                    R.id.item_10 -> {
                        presenter.limitItem = 10
                    }
                    R.id.item_30 -> {
                        presenter.limitItem = 30
                    }
                    R.id.item_60 -> {
                        presenter.limitItem = 60
                    }
                }
                currentItemId = id
            }
        })
        presenter.getLeagues()
    }
    private var currentItemId:Int? = null
}

package com.forderation.footballclubstudio.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.presenter.LeaguesPresenter
import com.forderation.footballclubstudio.activity.view.LeaguesView
import com.forderation.footballclubstudio.adapter.LeagueAdapter
import com.forderation.footballclubstudio.model.league.League
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
    }

    override fun hideLoading() {
        snackBar.dismiss()
        swipe_layout.isRefreshing = false
    }

    override fun loadFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leagues)
        adapter = LeagueAdapter(arrayListOf(), this) {}
        snackBar =
            Snackbar.make(swipe_layout, "Now run to get league ...", Snackbar.LENGTH_INDEFINITE)
        presenter = LeaguesPresenter(this)
        rv_leagues.layoutManager = GridLayoutManager(this, 2)
        rv_leagues.adapter = adapter
        swipe_layout.onRefresh {
            adapter.refreshAdapter()
            presenter.getLeagues()
        }
        presenter.initSpinner(spinner, applicationContext)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                adapter.refreshAdapter()
                when (spinner.selectedItem.toString()) {
                    "10" -> {
                        presenter.limitItem = 10
                    }
                    "30" -> {
                        presenter.limitItem = 30
                    }
                    "More than 60" -> {
                        presenter.limitItem = 60
                    }
                }
            }

        }
        presenter.getLeagues()
    }
}

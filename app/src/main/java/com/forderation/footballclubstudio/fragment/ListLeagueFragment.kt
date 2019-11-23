package com.forderation.footballclubstudio.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.LeagueDetailActivity
import com.forderation.footballclubstudio.activity.presenter.LeaguesPresenter
import com.forderation.footballclubstudio.activity.view.LeaguesView
import com.forderation.footballclubstudio.adapter.LeagueAdapter
import com.forderation.footballclubstudio.model.league.League
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list_league.*
import org.jetbrains.anko.support.v4.onRefresh

class ListLeagueFragment : Fragment(),LeaguesView {

    private lateinit var snackBar: Snackbar
    private lateinit var presenter: LeaguesPresenter
    private lateinit var adapter: LeagueAdapter
    private var ctx: Context? = null

    fun setLimitItem(item: Int){
        adapter.clearAdapter()
        presenter.limitItem = item
    }

    companion object{
        fun newInstance(ctx: Context): ListLeagueFragment {
            val fg = ListLeagueFragment()
            fg.ctx = ctx
            return fg
        }
    }

    override fun addLeague(item: League) {
        adapter.addItem(item)
    }

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
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_league, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LeagueAdapter(arrayListOf(), ctx!!) {
            val intent = Intent(ctx, LeagueDetailActivity::class.java)
            intent.putExtra(LeagueDetailActivity.LEAGUE_INTENT, it)
            startActivity(intent)
        }
        snackBar =
            Snackbar.make(swipe_layout, "Now run to get league ...", Snackbar.LENGTH_INDEFINITE)
        presenter = LeaguesPresenter(this, ctx!!)
        rv_leagues.layoutManager = GridLayoutManager(ctx, 2)
        rv_leagues.adapter = adapter
        presenter.getLeagues()
        swipe_layout.onRefresh {
            adapter.clearAdapter()
            presenter.getLeagues()
        }
    }

}

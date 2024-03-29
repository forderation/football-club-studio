package com.forderation.footballclubstudio.fragment

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
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.model.league.League
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_list_league.*
import org.jetbrains.anko.support.v4.onRefresh

class ListLeagueFragment : Fragment(),LeaguesView {

    private lateinit var snackBar: Snackbar
    private lateinit var presenter: LeaguesPresenter
    private lateinit var adapter: LeagueAdapter

    companion object{
        const val ITEM_INTENT = "ITEM_INTENT"
        fun newInstance(item: Int): ListLeagueFragment {
            val bundle = Bundle()
            bundle.putInt(ITEM_INTENT, item)
            val fg = ListLeagueFragment()
            fg.arguments = bundle
            return fg
        }
    }

    override fun addLeague(listItem: List<League>) {
        adapter.addItem(listItem[0])
    }

    override fun showLoading() {
        snackBar.show()
        if(progress_loading != null){
            progress_loading.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        snackBar.dismiss()
        if(progress_loading != null && swipe_layout != null){
            progress_loading.visibility = View.INVISIBLE
            swipe_layout.isRefreshing = false
        }
    }

    override fun loadFail(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_league, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LeagueAdapter(arrayListOf(), context!!) {
            val intent = Intent(context, LeagueDetailActivity::class.java)
            intent.putExtra(LeagueDetailActivity.LEAGUE_INTENT, it)
            startActivity(intent)
        }
        snackBar =
            Snackbar.make(swipe_layout, getString(R.string.loading_leagues), Snackbar.LENGTH_INDEFINITE)
        presenter = LeaguesPresenter(this, Gson(), ApiClient())
        rv_leagues.layoutManager = GridLayoutManager(context, 2)
        rv_leagues.adapter = adapter
        adapter.clearAdapter()
        val bundle = arguments
        if(bundle != null){
            presenter.limitItem = bundle.getInt(ITEM_INTENT,10)
        }else{
            presenter.initList()
        }
        swipe_layout.onRefresh {
            adapter.clearAdapter()
            presenter.initList()
        }
    }

}

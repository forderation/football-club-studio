package com.forderation.footballclubstudio.activity.presenter

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.view.LeaguesView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.model.league.GetLeagues
import com.forderation.footballclubstudio.model.league.League
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call

class LeaguesPresenter(private val view: LeaguesView) {

    private fun initList(): List<League>? {
        val getLeagues: Call<GetLeagues> = ApiClient.service.listLeagues()
        return getLeagues.execute().body()?.leagues
    }

    fun getLeagues() {
        view.showLoading()
        doAsync {
            val listLeagues: List<League>? = initList()
            val updatedList: MutableList<League> = arrayListOf()
            listLeagues?.forEach {
                if (it.id != null) {
                    val api = ApiClient.service.detailLeague(it.id)
                    val league:League? = api.execute().body()?.leagues?.get(0)
                    if(league!=null){
                        if(league.type.equals("Soccer")){
                            updatedList.add(league)
                            uiThread {
                                view.addLeague(league)
                            }
                        }
                    }
                }
            }
            uiThread {
                view.hideLoading()
            }
        }
    }

    fun initSpinner(spinner:Spinner, ctx:Context){
        ArrayAdapter.createFromResource(ctx, R.array.choice_total_league, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
    }
}
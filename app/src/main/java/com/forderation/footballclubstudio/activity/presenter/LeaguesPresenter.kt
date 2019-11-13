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
    private val typeLeague = "Soccer"
    var updatedList: MutableList<League> = arrayListOf()

    var limitItem = 10
        set(value) {
            field = value
            getLeagues()
        }

    private fun initList(): List<League>? {
        val getLeagues: Call<GetLeagues> = ApiClient.service.listLeagues()
        return getLeagues.execute().body()?.leagues
    }

    fun getLeagues() {
        view.showLoading()
        doAsync {
            val listLeagues: List<League>? = initList()
            if(limitItem != 60){
                for(x in 0 until limitItem){
                    if(listLeagues?.get(x) != null){
                        filterLeague(listLeagues[x])
                    }
                }
            }else{
                listLeagues?.forEach {
                    filterLeague(it)
                }
            }
            uiThread {
                view.hideLoading()
            }
        }
    }

    private fun filterLeague(leagueOld: League){
        doAsync {
            if (leagueOld.id != null) {
                val api = ApiClient.service.detailLeague(leagueOld.id)
                val league:League? = api.execute().body()?.leagues?.get(0)
                if(league!=null){
                    if(league.type.equals(typeLeague)){
                        updatedList.add(league)
                        uiThread {
                            view.addLeague(league)
                        }
                    }
                }
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
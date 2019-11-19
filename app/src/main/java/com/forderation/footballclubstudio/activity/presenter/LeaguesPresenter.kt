package com.forderation.footballclubstudio.activity.presenter

import android.content.Context
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.view.LeaguesView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.model.league.GetLeagues
import com.forderation.footballclubstudio.model.league.League
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call

class LeaguesPresenter(private val view: LeaguesView, ctx: Context) {
    private val typeLeague = ctx.resources.getString(R.string.sport_type)
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
            if (limitItem != 60) {
                for (x in 0 until limitItem) {
                    if (listLeagues?.get(x) != null && listLeagues[x].type.equals(typeLeague)) {
                        showLeague(listLeagues[x])
                    }
                }
            } else {
                listLeagues?.forEach {
                    if (it.type.equals(typeLeague)) {
                        showLeague(it)
                    }
                }
            }
            uiThread {
                view.hideLoading()
            }
        }
    }

    private fun showLeague(leagueOld: League) {
        doAsync {
            if (leagueOld.id != null) {
                val api = ApiClient.service.detailLeague(leagueOld.id)
                val league: League? = api.execute().body()?.leagues?.get(0)
                if (league != null) {
                    updatedList.add(league)
                    uiThread {
                        view.addLeague(league)
                    }
                }
            }
        }
    }
}
package com.forderation.footballclubstudio.activity.presenter

import android.content.Context
import com.forderation.footballclubstudio.BuildConfig
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.view.LeaguesView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.league.GetLeagues
import com.forderation.footballclubstudio.model.league.League
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LeaguesPresenter(private val view: LeaguesView, private val gson: Gson,private val apiClient: ApiClient) {
    private val typeLeague = BuildConfig.TYPE_SPORT

    var limitItem = 10
        set(value) {
            field = value
            getLeagues()
        }

    private fun initList(): List<League>? {
        var resp:GetLeagues? = null
        GlobalScope.launch {
            resp = gson.fromJson(apiClient.doRequest(Endpoints.getListLeague()).await(),GetLeagues::class.java)
        }
        return resp?.leagues
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
        GlobalScope.launch {
            val resp = gson.fromJson(apiClient.doRequest(Endpoints.getDetailLeague(leagueOld.id!!)).await(),GetLeagues::class.java)
            if(resp.leagues != null){
                view.addLeague(resp.leagues[0])
            }
        }
    }
}
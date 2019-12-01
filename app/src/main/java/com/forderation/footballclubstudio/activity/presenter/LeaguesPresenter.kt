package com.forderation.footballclubstudio.activity.presenter

import android.util.Log
import com.forderation.footballclubstudio.BuildConfig
import com.forderation.footballclubstudio.activity.view.LeaguesView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.league.GetLeagues
import com.forderation.footballclubstudio.model.league.League
import com.forderation.footballclubstudio.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LeaguesPresenter(private val view: LeaguesView,
                       private val gson: Gson,
                       private val apiClient: ApiClient,
                       private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    private val typeLeague = BuildConfig.TYPE_SPORT

    var limitItem = 10
        set(value) {
            field = value
            initList()
        }

    private var listLeagues: List<League>? = null

    fun initList() {
        GlobalScope.launch(context.main){
            val resp = gson.fromJson(apiClient.doRequestAsync(Endpoints.getListLeague()).await(),GetLeagues::class.java)
            listLeagues = resp?.leagues
            getLeagues()
        }
    }

    private fun getLeagues() {
        view.showLoading()
        GlobalScope.launch(context.main){
            if (limitItem != 60) {
                for (x in 0 until limitItem) {
                    if (listLeagues?.get(x) != null && listLeagues!![x].type.equals(typeLeague)) {
                        showLeague(listLeagues!![x].id!!)
                    }
                }
            } else {
                listLeagues?.forEach {
                    if (it.type.equals(typeLeague)) {
                        Log.d("logtag","showleague" + it)
                        showLeague(it.id!!)
                    }
                }
            }
            view.hideLoading()
        }
    }

    fun showLeague(idLeague: String) {
        GlobalScope.launch(context.main) {
            val resp = gson.fromJson(apiClient.doRequestAsync(Endpoints.getDetailLeague(idLeague)).await(),GetLeagues::class.java)
            if(resp?.leagues?.isNotEmpty()!!){
                view.addLeague(resp.leagues[0])
            }
        }
    }
}
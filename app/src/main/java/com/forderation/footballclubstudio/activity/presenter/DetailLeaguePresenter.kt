package com.forderation.footballclubstudio.activity.presenter

import com.forderation.footballclubstudio.activity.view.DetailLeagueView
import com.forderation.footballclubstudio.api.ApiClient
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailLeaguePresenter(private val view: DetailLeagueView) {

    fun getClubList(leagueName: String) {
        doAsync {
            val api = ApiClient.service.listClub(leagueName)
            val clubs = api.execute().body()?.clubs
            uiThread {
                if (clubs != null) {
                    view.showListClub(clubs)
                }
            }
        }
    }
}
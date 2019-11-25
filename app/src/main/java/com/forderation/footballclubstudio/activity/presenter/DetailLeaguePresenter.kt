package com.forderation.footballclubstudio.activity.presenter

import com.forderation.footballclubstudio.activity.view.DetailLeagueView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.club.GetTeams
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailLeaguePresenter(
    private val view: DetailLeagueView, private val gson: Gson,
    private val apiClient: ApiClient
) {

    fun getClubList(leagueName: String) {
        GlobalScope.launch {
            val resp = gson.fromJson(
                apiClient.doRequest(
                    Endpoints.getListClub(leagueName)
                ).await(), GetTeams::class.java
            )
            view.showListClub(resp.clubs)
        }
    }
}
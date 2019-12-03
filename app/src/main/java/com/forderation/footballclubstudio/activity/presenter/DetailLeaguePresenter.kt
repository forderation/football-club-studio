package com.forderation.footballclubstudio.activity.presenter

import com.forderation.footballclubstudio.activity.view.DetailLeagueView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.club.GetTeams
import com.forderation.footballclubstudio.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailLeaguePresenter(
    private val view: DetailLeagueView,
    private val gson: Gson,
    private val apiClient: ApiClient,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getClubList(leagueName: String) {
        GlobalScope.launch(context.main) {
            val resp = gson.fromJson(
                apiClient.doRequestAsync(
                    Endpoints.getListClub(
                        leagueName.replace(
                            " ",
                            "%20"
                        )
                    )
                ).await(), GetTeams::class.java
            )
            if (resp.clubs != null) {
                view.showListClub(resp.clubs)
            }
        }
    }
}
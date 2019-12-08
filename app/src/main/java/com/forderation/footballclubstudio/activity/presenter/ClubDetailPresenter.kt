package com.forderation.footballclubstudio.activity.presenter

import com.forderation.footballclubstudio.activity.view.ClubDetailView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.club.GetClub
import com.forderation.footballclubstudio.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ClubDetailPresenter(
    private val view: ClubDetailView, private val gson: Gson,
    private val apiClient: ApiClient, private val coroutineContext: CoroutineContextProvider = CoroutineContextProvider()
){

    fun getDetailClub(id:String?){
        GlobalScope.launch(coroutineContext.main){
            val resp = gson.fromJson(
                apiClient.doRequestAsync(Endpoints.getDetailTeam(id!!)).await(),
                GetClub::class.java
            )
            if(resp!=null){
                if(resp.clubs!=null){
                    view.inflateDetailView(resp.clubs[0])
                }
            }
        }
    }

}

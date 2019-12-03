package com.forderation.footballclubstudio.activity.presenter

import com.forderation.footballclubstudio.activity.view.DetailEventView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.event.GetEvents
import com.forderation.footballclubstudio.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailEventPresenter(
    private val view: DetailEventView, private val gson: Gson,
    private val apiClient: ApiClient, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getDetailEvent(idEvent: String) {
        GlobalScope.launch(context.main){
            val resp = gson.fromJson(
                apiClient.doRequestAsync(Endpoints.getDetailEvent(idEvent)).await(), GetEvents::class.java
            )
            view.showDetailEvent(resp.events!!)
        }
    }
}
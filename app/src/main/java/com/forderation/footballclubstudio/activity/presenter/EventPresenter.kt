package com.forderation.footballclubstudio.activity.presenter

import com.forderation.footballclubstudio.activity.view.ListEventView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.event.GetEvents
import com.forderation.footballclubstudio.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Suppress("SpellCheckingInspection")
class EventPresenter(
    private val view: ListEventView, private val gson: Gson, private val api: ApiClient,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getListEventFav() {
        view.inflateEventFav()
    }

    fun getLatestEvenMatch(idLeague: String) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(api.doRequestAsync(
                Endpoints.getLatestEvent(idLeague)).await(),
                GetEvents::class.java)
            view.inflateListEvent(data.events)
        }
    }

    fun getNextEvenMatch(idLeague: String) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(api.doRequestAsync(Endpoints.getNextEvent(idLeague)).await(), GetEvents::class.java)
            if(data!=null){
                view.inflateListEvent(data.events)
            }
        }
    }
}
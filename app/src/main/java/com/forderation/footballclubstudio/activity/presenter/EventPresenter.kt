package com.forderation.footballclubstudio.activity.presenter

import android.content.Context
import com.forderation.footballclubstudio.activity.view.ListEventView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.fragment.EventFragment
import com.forderation.footballclubstudio.model.event.GetEvents
import com.forderation.footballclubstudio.db.FavEvent
import com.forderation.footballclubstudio.db.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call

class EventPresenter(
    private val view: ListEventView,
    private val typeEvent: Int,
    private val ctx: Context
) {

    fun getListEventFav() {
        ctx.database.use {
            val result = select(FavEvent.TABLE_FAV_EVENT)
            val favorites = result.parseList(classParser<FavEvent>())
            view.inflateEventFav(favorites)
        }
    }

    fun getListEventLatestMatch(idLeague: String) {
        doAsync {
            var api: Call<GetEvents>? = null
            when (typeEvent) {
                (EventFragment.LATEST_MATCH) -> api = ApiClient.service.listPastEvent(idLeague)
                (EventFragment.UPCOMING_MATCH) -> api = ApiClient.service.listNextEvent(idLeague)
            }
            val listEvent = api?.execute()?.body()?.events
            uiThread {
                if (listEvent != null) {
                    view.inflateListEvent(listEvent)
                }
            }
        }
    }
}
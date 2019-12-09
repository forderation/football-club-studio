package com.forderation.footballclubstudio.activity.presenter

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.view.DetailEventView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.db.FavEvent
import com.forderation.footballclubstudio.db.database
import com.forderation.footballclubstudio.db.toEvent
import com.forderation.footballclubstudio.model.event.Event
import com.forderation.footballclubstudio.model.event.GetEvents
import com.forderation.footballclubstudio.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailEventPresenter(
    private val view: DetailEventView, private val gson: Gson,
    private val apiClient: ApiClient, private val coroutineContext: CoroutineContextProvider = CoroutineContextProvider()
) {

    lateinit var context:Context

    fun getDetailEvent(idEvent: String) {
        GlobalScope.launch(coroutineContext.main){
            val resp = gson.fromJson(
                apiClient.doRequestAsync(Endpoints.getDetailEvent(idEvent)).await(), GetEvents::class.java
            )
            view.showDetailEvent(resp.events!![0])
        }
    }

    fun getDetailEventByDB(id: String){
        context.database.use {
            val result = select(FavEvent.TABLE_FAV_EVENT)
                .whereArgs(
                    "(IdEvent = {idEvent})",
                    "idEvent" to id
                )
            val favourites = result.parseList(classParser<FavEvent>())
            view.showDetailEvent(favourites[0].toEvent())
        }
    }

    fun addToFav(mEvent:Event, id:String, homeImgUrl: String, awayImgUrl:String){
        try {
            context.database.use {
                insert(
                    FavEvent.TABLE_FAV_EVENT,
                    FavEvent.IdLeague to mEvent.idLeague,
                    FavEvent.IdEvent to id,
                    FavEvent.Name to mEvent.name,
                    FavEvent.Time to mEvent.time,
                    FavEvent.Date to mEvent.date,
                    FavEvent.HomeScore to mEvent.homeScore,
                    FavEvent.AwayScore to mEvent.awayScore,
                    FavEvent.Round to mEvent.round,
                    FavEvent.League to mEvent.strLeague,
                    FavEvent.HomeTeam to mEvent.homeTeam,
                    FavEvent.AwayTeam to mEvent.awayTeam,
                    FavEvent.HomeGoalDetails to mEvent.strHomeGoalDetails,
                    FavEvent.HomeRedCards to mEvent.strHomeRedCards,
                    FavEvent.HomeYellowCards to mEvent.strHomeYellowCards,
                    FavEvent.HomeLineupGoalkeeper to mEvent.strHomeLineupGoalkeeper,
                    FavEvent.HomeLineupDefense to mEvent.strHomeLineupDefense,
                    FavEvent.HomeLineupMidfield to mEvent.strHomeLineupMidfield,
                    FavEvent.HomeLineupForward to mEvent.strHomeLineupForward,
                    FavEvent.HomeLineupSubstitutes to mEvent.strHomeLineupSubstitutes,
                    FavEvent.AwayGoalDetails to mEvent.strAwayGoalDetails,
                    FavEvent.AwayRedCards to mEvent.strAwayRedCards,
                    FavEvent.AwayYellowCards to mEvent.strAwayYellowCards,
                    FavEvent.AwayLineupGoalkeeper to mEvent.strAwayLineupGoalkeeper,
                    FavEvent.AwayLineupDefense to mEvent.strAwayLineupDefense,
                    FavEvent.AwayLineupMidfield to mEvent.strAwayLineupMidfield,
                    FavEvent.AwayLineupForward to mEvent.strAwayLineupForward,
                    FavEvent.AwayLineupSubstitutes to mEvent.strAwayLineupSubstitutes,
                    FavEvent.HomeBadge to homeImgUrl,
                    FavEvent.AwayBadge to awayImgUrl,
                    FavEvent.HomeFormation to mEvent.strHomeFormation,
                    FavEvent.AwayFormation to mEvent.strAwayFormation,
                    FavEvent.IdHome to mEvent.idHome,
                    FavEvent.IdAway to mEvent.idAway
                )
            }
            view.showMsg(context.resources.getString(R.string.fav_added))
        } catch (e: SQLiteConstraintException) {
            view.showMsg(e.toString())
        }
    }

    fun removeFav(id: String){
        try {
            context.database.use {
                delete(FavEvent.TABLE_FAV_EVENT, "${FavEvent.IdEvent} = $id")
            }
        } catch (e: SQLiteConstraintException) {
            view.showMsg(e.toString())
        }
    }

    fun checkFav(id:String):Boolean{
        var isFav = false
        context.database.use {
            val result = select(FavEvent.TABLE_FAV_EVENT)
                .whereArgs(
                    "(IdEvent = {idEvent})",
                    "idEvent" to id
                )
            val favourites = result.parseList(classParser<FavEvent>())
            isFav = favourites.isNotEmpty()
        }
        return isFav
    }
}
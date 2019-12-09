package com.forderation.footballclubstudio.activity.presenter

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.view.ClubDetailView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.db.FavClub
import com.forderation.footballclubstudio.db.database
import com.forderation.footballclubstudio.db.toClub
import com.forderation.footballclubstudio.model.club.Club
import com.forderation.footballclubstudio.model.club.GetClub
import com.forderation.footballclubstudio.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class ClubDetailPresenter(
    private val view: ClubDetailView, private val gson: Gson,
    private val apiClient: ApiClient, private val coroutineContext: CoroutineContextProvider = CoroutineContextProvider()
){
    lateinit var context: Context

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

    fun addToFav(mClub: Club, id:String){
        try {
            context.database.use {
                insert(
                    FavClub.TABLE_FAV_CLUB,
                    FavClub.id to id,
                    FavClub.idTeam to mClub.idTeam,
                    FavClub.idLeague to mClub.idLeague,
                    FavClub.name to mClub.name,
                    FavClub.badge to mClub.badge,
                    FavClub.strStadium to mClub.strStadium,
                    FavClub.strStadiumThumb to mClub.strStadiumThumb,
                    FavClub.strStadiumDescription to mClub.strStadiumDescription,
                    FavClub.strStadiumLocation to mClub.strStadiumLocation,
                    FavClub.strDescriptionEN to mClub.strDescriptionEN,
                    FavClub.strTeamJersey to mClub.strTeamJersey,
                    FavClub.strTeamFanart1 to mClub.strTeamFanart1,
                    FavClub.strTeamFanart2 to mClub.strTeamFanart2,
                    FavClub.strTeamFanart3 to mClub.strTeamFanart3,
                    FavClub.strTeamFanart4 to mClub.strTeamFanart4,
                    FavClub.intFormedYear to mClub.intFormedYear,
                    FavClub.strWebsite to mClub.strWebsite,
                    FavClub.strFacebook to mClub.strFacebook,
                    FavClub.strTwitter to mClub.strTwitter,
                    FavClub.strInstagram  to mClub.strInstagram
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
                delete(FavClub.TABLE_FAV_CLUB, "${FavClub.idTeam} = $id")
            }
        } catch (e: SQLiteConstraintException) {
            view.showMsg(e.toString())
        }
    }

    fun getDetailClubByDB(id: String){
        context.database.use {
            val result = select(FavClub.TABLE_FAV_CLUB)
                .whereArgs(
                    "${FavClub.idTeam} = $id"
                )
            val favourites = result.parseList(classParser<FavClub>())
            view.inflateDetailView(favourites[0].toClub())
        }
    }

    fun checkFav(id:String):Boolean{
        var isFav = false
        context.database.use {
            val result = select(FavClub.TABLE_FAV_CLUB)
                .whereArgs(
                    "${FavClub.idTeam} = $id"
                )
            val favourites = result.parseList(classParser<FavClub>())
            isFav = favourites.isNotEmpty()
        }
        return isFav
    }
}

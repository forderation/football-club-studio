package com.forderation.footballclubstudio.api

import com.forderation.footballclubstudio.model.club.GetTeams
import com.forderation.footballclubstudio.model.event.GetEvents
import com.forderation.footballclubstudio.model.event.SearchEvent
import com.forderation.footballclubstudio.model.league.GetLeagues
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoints {
    @GET("api/v1/json/1/all_leagues.php")
    fun listLeagues(): Call<GetLeagues>

    @GET("api/v1/json/1/lookupleague.php?")
    fun detailLeague(@Query("id") id: String): Call<GetLeagues>

    @GET("api/v1/json/1/search_all_teams.php?")
    fun listClub(@Query("l") nameLeague: String): Call<GetTeams>

    @GET("api/v1/json/1/eventspastleague.php?")
    fun listPastEvent(@Query("id") idLeague: String): Call<GetEvents>

    @GET("api/v1/json/1/eventsnextleague.php?")
    fun listNextEvent(@Query("id") idLeague: String): Call<GetEvents>

    @GET("api/v1/json/1/searchevents.php?")
    fun searchEvent(@Query("e") nameEvent: String): Call<SearchEvent>
}
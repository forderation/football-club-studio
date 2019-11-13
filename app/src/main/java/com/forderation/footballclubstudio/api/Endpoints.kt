package com.forderation.footballclubstudio.api

import com.forderation.footballclubstudio.model.league.GetLeagues
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoints{
    @GET("api/v1/json/1/all_leagues.php")
    fun listLeagues(): Call<GetLeagues>

    @GET("api/v1/json/1/lookupleague.php?")
    fun detailLeague(@Query("id") id:String):Call<GetLeagues>
}
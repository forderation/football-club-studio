package com.forderation.footballclubstudio.activity.view

import com.forderation.footballclubstudio.model.league.League

interface LeaguesView{
    fun showLoading()
    fun hideLoading()
    fun loadFail(msg:String)
    fun addLeague(item: League)
}
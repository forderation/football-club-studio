package com.forderation.footballclubstudio.api

object Endpoints {
    fun getListLeague():String = "api/v1/json/1/all_leagues.php"

    fun getDetailLeague(idLeague:String) = "api/v1/json/1/lookupleague.php?id=$idLeague"

    fun getListClub(nameLeague:String) = "api/v1/json/1/search_all_teams.php?l=$nameLeague"

    fun getDetailTeam(idTeam:String) = "api/v1/json/1/lookupteam.php?id=$idTeam"

    fun getDetailEvent(idEvent: String) = "api/v1/json/1/lookupevent.php?id=$idEvent"

    fun getLatestEvent(idLeague: String) = "api/v1/json/1/eventspastleague.php?id=$idLeague"

    fun getNextEvent(idLeague: String) = "api/v1/json/1/eventsnextleague.php?id=$idLeague"

    fun getSearchEvent(eventName: String) = "api/v1/json/1/searchevents.php?e=$eventName"
}
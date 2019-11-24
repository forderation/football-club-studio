package com.forderation.footballclubstudio.db

import android.os.Parcelable
import com.forderation.footballclubstudio.model.event.Event
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavEvent(
    val id: Long?,
    val idEvent: String?,
    val idLeague: String?,
    val round: String?,
    val name: String?,
    val strSport: String?,
    val date: String?,
    val strLeague: String?,
    val time: String?,
    val idHome: String?,
    val idAway: String?,
    val homeTeam: String?,
    val awayTeam: String?,
    val homeScore: String?,
    val awayScore: String?,
    val thumbnail: String?,
    val strHomeGoalDetails: String?,
    val strHomeRedCards: String?,
    val strHomeYellowCards: String?,
    val strHomeLineupGoalkeeper: String?,
    val strHomeLineupDefense: String?,
    val strHomeLineupMidfield: String?,
    val strHomeLineupForward: String?,
    val strHomeLineupSubstitutes: String?,
    val strHomeFormation: String?,
    val strAwayGoalDetails: String?,
    val strAwayRedCards: String?,
    val strAwayYellowCards: String?,
    val strAwayLineupGoalkeeper: String?,
    val strAwayLineupDefense: String?,
    val strAwayLineupMidfield: String?,
    val strAwayLineupForward: String?,
    val strAwayLineupSubstitutes: String?,
    val strAwayFormation: String?,
    val homeBadge: String?,
    val awayBadge: String?
) : Parcelable {
    companion object {
        const val TABLE_FAV_EVENT = "TABLE_FAV_EVENT"
        const val ID = "ID_"
        const val IdEvent = "IdEvent"
        const val IdLeague = "IdLeague"
        const val Round = "Round"
        const val Name = "Name"
        const val Sport = "Sport"
        const val Date = "Date"
        const val League = "League"
        const val Time = "Time"
        const val IdHome = "IdHome"
        const val IdAway = "IdAway"
        const val HomeTeam = "HomeTeam"
        const val AwayTeam = "AwayTeam"
        const val HomeScore = "HomeScore"
        const val AwayScore = "AwayScore"
        const val Thumbnail = "Thumbnail"
        const val HomeGoalDetails = "HomeGoalDetails"
        const val HomeRedCards = "HomeRedCards"
        const val HomeYellowCards = "HomeYellowCards"
        const val HomeLineupGoalkeeper = "HomeLineupGoalkeeper"
        const val HomeLineupDefense = "HomeLineupDefense"
        const val HomeLineupMidfield = "HomeLineupMidfield"
        const val HomeLineupForward = "HomeLineupForward"
        const val HomeLineupSubstitutes = "HomeLineupSubstitutes"
        const val HomeFormation = "HomeFormation"
        const val AwayGoalDetails = "AwayGoalDetails"
        const val AwayRedCards = "AwayRedCards"
        const val AwayYellowCards = "AwayYellowCards"
        const val AwayLineupGoalkeeper = "AwayLineupGoalkeeper"
        const val AwayLineupDefense = "AwayLineupDefense"
        const val AwayLineupMidfield = "AwayLineupMidfield"
        const val AwayLineupForward = "AwayLineupForward"
        const val AwayLineupSubstitutes = "AwayLineupSubstitutes"
        const val AwayFormation = "AwayFormation"
        const val HomeBadge = "HomeBadge"
        const val AwayBadge = "AwayBadge"
    }
}

fun FavEvent.toEvent() = Event(
    idEvent = idEvent,
    name = name,
    time = time,
    date = date,
    homeScore = homeScore,
    awayScore = awayScore,
    round = round,
    strLeague = strLeague,
    homeTeam = homeTeam,
    awayTeam = awayTeam,
    strHomeGoalDetails = strHomeGoalDetails,
    strHomeRedCards = strHomeRedCards,
    strHomeYellowCards = strHomeYellowCards,
    strHomeLineupGoalkeeper = strHomeLineupGoalkeeper,
    strHomeLineupDefense = strHomeLineupDefense,
    strHomeLineupMidfield = strHomeLineupMidfield,
    strHomeLineupForward = strHomeLineupForward,
    strHomeLineupSubstitutes = strHomeLineupSubstitutes,
    strAwayGoalDetails = strAwayGoalDetails,
    strAwayRedCards = strAwayRedCards,
    strAwayYellowCards = strAwayYellowCards,
    strAwayLineupGoalkeeper = strAwayLineupGoalkeeper,
    strAwayLineupDefense = strAwayLineupDefense,
    strAwayLineupMidfield = strAwayLineupMidfield,
    strAwayLineupForward = strAwayLineupForward,
    strAwayLineupSubstitutes = strAwayLineupSubstitutes,
    strHomeFormation = strHomeFormation,
    strAwayFormation = strAwayFormation,
    idLeague = idLeague,
    thumbnail = thumbnail,
    idAway = idAway,
    idHome = idHome,
    strSport = strSport
)
package com.forderation.footballclubstudio.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavEvent(
    val id: Long?,
    val round: String?,
    val idMatch: String?,
    val idLeague: String?,
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
        const val Round = "Round"
        const val IdMatch = "IdMatch"
        const val IdLeague = "IdLeague"
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
    fun getHomeBadgeSmall():String{
        return homeBadge.plus("/preview")
    }
    fun getAwayBadgeSmall():String{
        return awayBadge.plus("/preview")
    }
}
package com.forderation.footballclubstudio.database

class FavEventContracts(
    val id: Long?,
    val round: String?,
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
    val homeBadge: ByteArray?,
    val awayBadge: ByteArray?
) {
    companion object {
        const val TABLE_FAV_EVENT = "TABLE_FAV_EVENT"
        const val ID = "ID"
        const val ROUND = "ROUND"
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
}
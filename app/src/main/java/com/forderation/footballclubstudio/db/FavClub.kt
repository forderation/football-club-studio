package com.forderation.footballclubstudio.db

import android.os.Parcelable
import com.forderation.footballclubstudio.model.club.Club
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavClub(
    val id: Long?,
    val idTeam: String?,
    val idLeague: String?,
    val name: String?,
    val badge: String?,
    val strStadium: String?,
    val strStadiumThumb: String?,
    val strStadiumDescription: String?,
    val strStadiumLocation: String?,
    val strDescriptionEN: String?,
    val strTeamJersey: String?,
    val strTeamFanart1: String?,
    val strTeamFanart2: String?,
    val strTeamFanart3: String?,
    val strTeamFanart4: String?,
    val intFormedYear: String?,
    val strWebsite: String?,
    val strFacebook: String?,
    val strTwitter: String?,
    val strInstagram: String?
) : Parcelable {
    companion object {
        const val TABLE_FAV_CLUB = "TABLE_FAV_CLUB"
        const val id = "ID_"
        const val idTeam = "idTeam"
        const val idLeague = "idLeague"
        const val name = "name"
        const val badge = "badge"
        const val strStadium = "strStadium"
        const val strStadiumThumb = "strStadiumThumb"
        const val strStadiumDescription = "strStadiumDescription"
        const val strStadiumLocation = "strStadiumLocation"
        const val strDescriptionEN = "strDescriptionEN"
        const val strTeamJersey = "strTeamJersey"
        const val strTeamFanart1 = "strTeamFanart1"
        const val strTeamFanart2 = "strTeamFanart2"
        const val strTeamFanart3 = "strTeamFanart3"
        const val strTeamFanart4 = "strTeamFanart4"
        const val intFormedYear = "intFormedYear"
        const val strWebsite = "strWebsite"
        const val strFacebook = "strFacebook"
        const val strTwitter = "strTwitter"
        const val strInstagram = "strInstagram"
    }
}

fun FavClub.toClub(): Club = Club(
    idTeam = idTeam,
    idLeague = idLeague,
    name = name,
    badge = badge,
    strStadium = strStadium,
    strStadiumThumb = strStadiumThumb,
    strStadiumDescription = strStadiumDescription,
    strStadiumLocation = strStadiumLocation,
    strDescriptionEN = strDescriptionEN,
    strTeamJersey = strTeamJersey,
    strTeamFanart1 = strTeamFanart1,
    strTeamFanart2 = strTeamFanart2,
    strTeamFanart3 = strTeamFanart3,
    strTeamFanart4 = strTeamFanart4,
    intFormedYear = intFormedYear,
    strWebsite = strWebsite,
    strFacebook = strFacebook,
    strTwitter = strTwitter,
    strInstagram = strInstagram
)
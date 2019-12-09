package com.forderation.footballclubstudio.model.club

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Club(
    @SerializedName("idTeam")
    val idTeam: String?,
    @SerializedName("idLeague")
    val idLeague: String?,
    @SerializedName("strTeam")
    val name: String?,
    @SerializedName("strTeamBadge")
    val badge: String?,
    @SerializedName("strStadium")
    val strStadium: String?,
    @SerializedName("strStadiumThumb")
    val strStadiumThumb: String?,
    @SerializedName("strStadiumDescription")
    val strStadiumDescription: String?,
    @SerializedName("strStadiumLocation")
    val strStadiumLocation: String?,
    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String?,
    @SerializedName("strTeamJersey")
    val strTeamJersey: String?,
    @SerializedName("strTeamFanart1")
    val strTeamFanart1: String?,
    @SerializedName("strTeamFanart2")
    val strTeamFanart2: String?,
    @SerializedName("strTeamFanart3")
    val strTeamFanart3: String?,
    @SerializedName("strTeamFanart4")
    val strTeamFanart4: String?,
    @SerializedName("intFormedYear")
    val intFormedYear: String?,
    @SerializedName("strWebsite")
    val strWebsite: String?,
    @SerializedName("strFacebook")
    val strFacebook: String?,
    @SerializedName("strTwitter")
    val strTwitter: String?,
    @SerializedName("strInstagram")
    val strInstagram: String?
    ) : Parcelable {
    fun getBadgeSmall(): String {
        return badge.plus("/preview")
    }
    fun getStadiumSmall(): String {
        return strStadiumThumb.plus("/preview")
    }
    fun getJerseySmall(): String {
        return strTeamJersey.plus("/preview")
    }
}
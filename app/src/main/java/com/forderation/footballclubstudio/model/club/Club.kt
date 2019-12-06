package com.forderation.footballclubstudio.model.club

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Club(
    @SerializedName("idTeam")
    val id: String?,
    @SerializedName("strTeam")
    val name: String?,
    @SerializedName("strTeamBadge")
    val badge: String?,
    @SerializedName("strAlternate")
    val alternate: String?,
    @SerializedName("strStadium")
    val strStadium: String?,
    @SerializedName("strStadiumThumb")
    val strStadiumThumb: String?,
    @SerializedName("strStadiumDescription")
    val strStadiumDescription: String?,
    @SerializedName("strStadiumLocation")
    val strStadiumLocation: String?,
    @SerializedName("intStadiumCapacity")
    val intStadiumCapacity: Int?,
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
    val strTeamFanart4: String?
) : Parcelable {
    fun getBadgeSmall(): String {
        return badge.plus("/preview")
    }
}
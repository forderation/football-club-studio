package com.forderation.footballclubstudio.model.club

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Club(
    @SerializedName("idTeam")
    val id:String?,
    @SerializedName("strTeam")
    val name:String?,
    @SerializedName("strTeamBadge")
    val badge:String?
) : Parcelable{
    public fun getBadgeSmall():String{
        return badge.plus("/preview")
    }
}
package com.forderation.footballclubstudio.model.league

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League(
    @SerializedName("idLeague")
    val id: String?,
    @SerializedName("strLeague")
    val name: String?,
    @SerializedName("strCountry")
    val country: String?,
    @SerializedName("strWebsite")
    val website: String?,
    @SerializedName("strDescriptionEN")
    val description: String?,
    @SerializedName("strBadge")
    val badge: String?,
    @SerializedName("strBanner")
    val banner: String?,
    @SerializedName("strFanart1")
    val background: String?,
    @SerializedName("strSport")
    val type:String?,
    @SerializedName("strTrophy")
    val trophy:String?
) : Parcelable{
    fun smallBackground():String{
        return background.plus("/preview")
    }
    fun getSmallTrophy():String{
        return trophy.plus("/preview")
    }
}
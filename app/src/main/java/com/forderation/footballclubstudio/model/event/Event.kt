package com.forderation.footballclubstudio.model.event

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    @SerializedName("idEvent")
    val id:String?,
    @SerializedName("idLeague")
    val idLeague:String?,
    @SerializedName("strEvent")
    val name:String?,
    @SerializedName("strSport")
    val strSport:String?,
    @SerializedName("strDate")
    val date:String?,
    @SerializedName("strTime")
    val time:String?,
    @SerializedName("idHomeTeam")
    val idHome:String?,
    @SerializedName("idAwayTeam")
    val idAway:String?,
    @SerializedName("strHomeTeam")
    val homeTeam:String?,
    @SerializedName("strAwayTeam")
    val awayTeam:String?,
    @SerializedName("intHomeScore")
    val homeScore:String?,
    @SerializedName("intAwayScore")
    val awayScore:String?,
    @SerializedName("strThumb")
    val thumbnail:String?,
    @SerializedName("strHomeGoalDetails")
    val strHomeGoalDetails:String?,
    @SerializedName("strHomeRedCards")
    val strHomeRedCards:String?,
    @SerializedName("strHomeYellowCards")
    val strHomeYellowCards:String?,
    @SerializedName("strHomeLineupGoalkeeper")
    val strHomeLineupGoalkeeper:String?,
    @SerializedName("strHomeLineupDefense")
    val strHomeLineupDefense:String?,
    @SerializedName("strHomeLineupMidfield")
    val strHomeLineupMidfield:String?,
    @SerializedName("strHomeLineupForward")
    val strHomeLineupForward:String?,
    @SerializedName("strHomeLineupSubstitutes")
    val strHomeLineupSubstitutes:String?,
    @SerializedName("strHomeFormation")
    val strHomeFormation:String?,
    @SerializedName("strAwayGoalDetails")
    val strAwayGoalDetails:String?,
    @SerializedName("strAwayRedCards")
    val strAwayRedCards:String?,
    @SerializedName("strAwayYellowCards")
    val strAwayYellowCards:String?,
    @SerializedName("strAwayLineupGoalkeeper")
    val strAwayLineupGoalkeeper:String?,
    @SerializedName("strAwayLineupDefense")
    val strAwayLineupDefense:String?,
    @SerializedName("strAwayLineupMidfield")
    val strAwayLineupMidfield:String?,
    @SerializedName("strAwayLineupForward")
    val strAwayLineupForward:String?,
    @SerializedName("strAwayLineupSubstitutes")
    val strAwayLineupSubstitutes:String?,
    @SerializedName("strAwayFormation")
    val strAwayFormation:String?
    ) : Parcelable
package com.forderation.footballclubstudio.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Club(val clubName:String,val clubDesc:String, val clubLogo:Int) : Parcelable
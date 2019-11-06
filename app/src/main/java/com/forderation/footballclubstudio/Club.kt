package com.forderation.footballclubstudio

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Club(val clubName:String,val clubDesc:String, val clubLogo:Int) : Parcelable
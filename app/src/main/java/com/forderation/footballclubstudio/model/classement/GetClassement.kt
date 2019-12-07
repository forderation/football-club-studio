package com.forderation.footballclubstudio.model.classement

import com.google.gson.annotations.SerializedName

class GetClassement (
    @SerializedName("table")
    val list: List<TeamClassement>?
)
package com.forderation.footballclubstudio.model.league

import com.google.gson.annotations.SerializedName

data class GetLeagues(
    @SerializedName("leagues")
    val leagues: List<League>?
)
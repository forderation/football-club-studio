package com.forderation.footballclubstudio.model.club

import com.google.gson.annotations.SerializedName

data class GetTeams(
    @SerializedName("teams")
    val clubs: List<Club>
)
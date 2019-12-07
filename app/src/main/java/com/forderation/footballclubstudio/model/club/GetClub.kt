package com.forderation.footballclubstudio.model.club

import com.google.gson.annotations.SerializedName

data class GetClub(
    @SerializedName("teams")
    val clubs: List<Club>?
)
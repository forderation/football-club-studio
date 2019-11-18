package com.forderation.footballclubstudio.model.event

import com.google.gson.annotations.SerializedName

data class SearchEvent(
    @SerializedName("event")
    val events: List<Event>
)
package com.forderation.footballclubstudio.model.event

import com.google.gson.annotations.SerializedName

data class GetEvents(
    @SerializedName("events")
    val events: List<Event>?
)
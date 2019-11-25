package com.forderation.footballclubstudio.activity.view

import com.forderation.footballclubstudio.model.event.Event

interface ListEventView {
    fun inflateListEvent(listEvent:List<Event>)
    fun inflateEventFav()
}
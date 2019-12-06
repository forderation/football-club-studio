package com.forderation.footballclubstudio.activity.view

import com.forderation.footballclubstudio.model.event.Event

interface DetailEventView{
    fun showDetailEvent(listEvent: List<Event>)
    fun showMsg(msg:String)
}
package com.forderation.footballclubstudio.activity.view

import com.forderation.footballclubstudio.model.event.Event

interface DetailEventView{
    fun showDetailEvent(mEvent: Event)
    fun showMsg(msg:String)
}
package com.forderation.footballclubstudio.activity.view

import com.forderation.footballclubstudio.model.club.Club

interface DetailLeagueView {
    fun showListClub(clubList: List<Club>)
    fun showBottomDesc(desc: String)
}
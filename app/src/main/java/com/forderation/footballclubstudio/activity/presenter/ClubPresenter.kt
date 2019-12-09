package com.forderation.footballclubstudio.activity.presenter

import android.content.Context
import com.forderation.footballclubstudio.activity.view.ListClubView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.club.GetClub
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ClubPresenter(private val view: ListClubView) {
    lateinit var context: Context
    fun getNetListClub(nameLeague: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val resp = Gson().fromJson(
                ApiClient().doRequestAsync(Endpoints.getListClub(nameLeague)).await(),
                GetClub::class.java
            )
            if (resp.clubs != null) {
                view.inflateListClub(resp.clubs)
            }
        }
    }
}
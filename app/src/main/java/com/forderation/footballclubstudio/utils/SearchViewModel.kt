package com.forderation.footballclubstudio.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.club.Club
import com.forderation.footballclubstudio.model.club.GetClub
import com.forderation.footballclubstudio.model.event.Event
import com.forderation.footballclubstudio.model.event.SearchEvent
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchViewModel(
    val gson: Gson = Gson(),
    val apiClient: ApiClient = ApiClient(),
    val context: CoroutineContextProvider = CoroutineContextProvider(),
    var listEvent: MutableLiveData<List<Event>> = MutableLiveData(),
    var onResponse: MutableLiveData<String> = MutableLiveData(),
    var onLoading: MutableLiveData<Boolean> = MutableLiveData(false)
) : ViewModel() {

    private var listClub: MutableLiveData<List<Club>> = MutableLiveData()

    fun getListNextMatch(nameEvent: String) {
        onLoading.value = true
        GlobalScope.launch(context.main) {
            val resp = gson.fromJson(
                apiClient.doRequestAsync(
                    Endpoints.getSearchEvent(
                        nameEvent.replace(
                            " ",
                            "%20"
                        )
                    )
                ).await(), SearchEvent::class.java
            )
            if (resp == null) {
                listEvent.value = null
                onResponse.value = "Search failure"
                onLoading.value = false
            } else {
                listEvent.value = resp.events
                onResponse.value = "Search success"
                onLoading.value = false
            }
        }
    }

    fun getListClub(nameTeam: String) {
        onLoading.value = true
        GlobalScope.launch(context.main) {
            val resp = gson.fromJson(
                apiClient.doRequestAsync(
                    Endpoints.getTeamByName(
                        nameTeam.replace(
                            " ",
                            "%20"
                        )
                    )
                ).await(), GetClub::class.java
            )
            if (resp == null) {
                listClub.value = null
                onResponse.value = "Search failure"
                onLoading.value = false
            } else {
                listClub.value = resp.clubs
                onResponse.value = "Search success"
                onLoading.value = false
            }
        }
    }

    fun listEvent(): MutableLiveData<List<Event>> {
        return listEvent
    }

    fun listTeam(): MutableLiveData<List<Club>>{
        return listClub
    }

    fun onResponse(): MutableLiveData<String> {
        return onResponse
    }

    fun onLoading(): MutableLiveData<Boolean> {
        return onLoading
    }
}
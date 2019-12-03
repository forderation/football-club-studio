package com.forderation.footballclubstudio.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.event.Event
import com.forderation.footballclubstudio.model.event.SearchEvent
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventViewModel(
    val gson: Gson = Gson(),
    val apiClient: ApiClient = ApiClient(),
    val context: CoroutineContextProvider = CoroutineContextProvider(),
    var listEvent: MutableLiveData<List<Event>> = MutableLiveData(),
    var onResponse: MutableLiveData<String> = MutableLiveData(),
    var onLoading: MutableLiveData<Boolean> = MutableLiveData(false)
) : ViewModel() {

    fun getData(nameEvent: String) {
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

    fun listEvent(): MutableLiveData<List<Event>> {
        return listEvent
    }

    fun onResponse(): MutableLiveData<String> {
        return onResponse
    }

    fun onLoading(): MutableLiveData<Boolean> {
        return onLoading
    }
}
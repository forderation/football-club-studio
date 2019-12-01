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

class EventViewModel : ViewModel() {
    private val context: CoroutineContextProvider = CoroutineContextProvider()
    private var listEvent: MutableLiveData<List<Event>> = MutableLiveData()
    private var onResponse: MutableLiveData<String> = MutableLiveData()
    private var onLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val gson = Gson()
    private val apiClient = ApiClient()

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
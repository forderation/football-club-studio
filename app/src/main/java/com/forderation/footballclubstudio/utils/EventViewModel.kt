package com.forderation.footballclubstudio.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.event.Event
import com.forderation.footballclubstudio.model.event.GetEvents
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {
    private var listEvent: MutableLiveData<List<Event>> = MutableLiveData()
    private var onResponse: MutableLiveData<String> = MutableLiveData()
    private var onLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val gson = Gson()
    private val apiClient = ApiClient()

    fun getData(nameEvent: String) {
        onLoading.value = true
        GlobalScope.launch {
            val resp = gson.fromJson(apiClient.doRequest(Endpoints.getSearchEvent(nameEvent)).await(),GetEvents::class.java)
            if(resp.events.isEmpty()){
                listEvent.value = null
                onResponse.value = "Search failure"
                onLoading.value = false
            }else{
                listEvent.value = resp.events
                onResponse.value = "Search success"
                onLoading.value = false
            }
        }
    }

    fun listEvent(): MutableLiveData<List<Event>> {
        return listEvent
    }

    fun onResponse():MutableLiveData<String>{
        return onResponse
    }

    fun onLoading():MutableLiveData<Boolean>{
        return onLoading
    }
}
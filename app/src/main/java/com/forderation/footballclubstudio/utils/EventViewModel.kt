package com.forderation.footballclubstudio.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.model.event.Event
import com.forderation.footballclubstudio.model.event.SearchEvent
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventViewModel : ViewModel() {
    private var listEvent: MutableLiveData<List<Event>> = MutableLiveData()
    private var onResponse: MutableLiveData<String> = MutableLiveData()
    private var onLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getData(nameEvent: String) {
        onLoading.value = true
        doAsync {
            val api = ApiClient.service.searchEvent(nameEvent)
            api.enqueue(object : Callback<SearchEvent> {
                override fun onFailure(call: Call<SearchEvent>, t: Throwable) {
                    uiThread {
                        listEvent.value = null
                        onResponse.value = "Search failure"
                        onLoading.value = false
                    }
                }

                override fun onResponse(call: Call<SearchEvent>, response: Response<SearchEvent>) {
                    if (response.isSuccessful) {
                        uiThread {
                            listEvent.value = response.body()?.events
                            onResponse.value = "Search success"
                            onLoading.value = false
                        }
                    }
                }
            })
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
package com.forderation.footballclubstudio.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.adapter.EventAdapter
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.model.club.Club
import kotlinx.android.synthetic.main.fragment_event_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EventFragment(private val idLeague:String, private val clubList: List<Club>) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_event.layoutManager = LinearLayoutManager(context)
        val api = ApiClient.service.listPastEvent(idLeague)
        doAsync {
            val listEvent = api.execute().body()?.events
            uiThread {
                if(listEvent!=null){
                    Log.d("debug_event"," ".plus(listEvent.size))
                    list_event.adapter = EventAdapter(listEvent,clubList){
                    }
                    list_event.adapter?.notifyDataSetChanged()
                }
            }
        }
    }
}

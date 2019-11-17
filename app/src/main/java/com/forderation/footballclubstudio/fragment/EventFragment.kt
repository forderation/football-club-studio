package com.forderation.footballclubstudio.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.presenter.EventPresenter
import com.forderation.footballclubstudio.activity.view.ListEventView
import com.forderation.footballclubstudio.adapter.EventAdapter
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.model.club.Club
import com.forderation.footballclubstudio.model.event.Event
import kotlinx.android.synthetic.main.fragment_event_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EventFragment(private val idLeague:String, private val clubList: List<Club>, private val typeEvent:Int) : Fragment(),
    ListEventView {

    companion object{
        const val LATEST_MATCH = 0
        const val UPCOMING_MATCH = 1
    }

    override fun inflateListEvent(listEvent: List<Event>) {
        mAdapter = EventAdapter(listEvent,clubList){}
        list_event.layoutManager = LinearLayoutManager(context)
        list_event.adapter = mAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    lateinit var mPresenter: EventPresenter
    lateinit var mAdapter: EventAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_event.layoutManager = LinearLayoutManager(context)
        mPresenter = EventPresenter(this, typeEvent)
        mPresenter.getListEventLatestMatch(idLeague)
    }
}

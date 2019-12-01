package com.forderation.footballclubstudio.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.EventDetailActivity
import com.forderation.footballclubstudio.activity.presenter.EventPresenter
import com.forderation.footballclubstudio.activity.view.ListEventView
import com.forderation.footballclubstudio.adapter.EventAdapter
import com.forderation.footballclubstudio.adapter.FavEventAdapter
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.db.FavEvent
import com.forderation.footballclubstudio.db.database
import com.forderation.footballclubstudio.db.toEvent
import com.forderation.footballclubstudio.model.event.Event
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_event_list.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class EventFragment : Fragment(),
    ListEventView {

    companion object {
        const val LATEST_MATCH = 0
        const val NEXT_MATCH = 1
        const val FAV_MATCH = 2
        const val ID_LEAGUE = "ID_LEAGUE"
        const val TYPE_EVENT = "TYPE_EVENT"
        const val REQUEST_UPDATE_FAV = 4
        fun newInstance(idLeague: String, typeEvent: Int): EventFragment {
            val bundle = Bundle()
            bundle.putString(ID_LEAGUE, idLeague)
            bundle.putInt(TYPE_EVENT, typeEvent)
            val fg = EventFragment()
            fg.arguments = bundle
            return fg
        }

        fun newInstance(typeEvent: Int): EventFragment {
            val bundle = Bundle()
            bundle.putInt(TYPE_EVENT, typeEvent)
            val fg = EventFragment()
            fg.arguments = bundle
            return fg
        }
    }

    override fun inflateListEvent(listEvent: List<Event>) {
        mAdapter = EventAdapter(listEvent) { e, h, a ->
            val intent = Intent(activity, EventDetailActivity::class.java)
            intent.putExtra(EventDetailActivity.EVENT_INTENT, e)
            intent.putExtra(EventDetailActivity.HOME_BADGE_URL, h)
            intent.putExtra(EventDetailActivity.AWAY_BADGE_URL, a)
            startActivity(intent)
        }
        if(list_event != null){
            list_event.layoutManager = LinearLayoutManager(context)
            list_event.adapter = mAdapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_UPDATE_FAV){
            if(resultCode == Activity.RESULT_OK){
                mPresenter.getListEventFav()
            }
        }
    }

    override fun inflateEventFav() {
        var favorites:List<FavEvent> = arrayListOf()
        context?.database?.use {
            val result = select(FavEvent.TABLE_FAV_EVENT)
            favorites = result.parseList(classParser())
        }
        mAdapterFav = FavEventAdapter(favorites) { e,h,a ->
            val intent = Intent(activity, EventDetailActivity::class.java)
            intent.putExtra(EventDetailActivity.EVENT_INTENT, e.toEvent())
            intent.putExtra(EventDetailActivity.HOME_BADGE_URL, h)
            intent.putExtra(EventDetailActivity.AWAY_BADGE_URL, a)
            startActivityForResult(intent, REQUEST_UPDATE_FAV)
        }
        list_event.layoutManager = LinearLayoutManager(context)
        list_event.adapter = mAdapterFav
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    private lateinit var mPresenter: EventPresenter
    private lateinit var mAdapter: EventAdapter
    private lateinit var mAdapterFav: FavEventAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_event.layoutManager = LinearLayoutManager(context)
        val bundle = arguments
        if (bundle != null) {
            val typeEvent = bundle.getInt(TYPE_EVENT)
            mPresenter = EventPresenter(this, Gson(), ApiClient())
            val idLeague = bundle.getString(ID_LEAGUE)
            if (typeEvent == FAV_MATCH) {
                mPresenter.getListEventFav()
            } else {
                when(typeEvent){
                    LATEST_MATCH -> {
                        mPresenter.getLatestEvenMatch(idLeague?:"")
                    }
                    NEXT_MATCH -> {
                        mPresenter.getNextEvenMatch(idLeague?:"")
                    }
                }
            }
        }
    }
}

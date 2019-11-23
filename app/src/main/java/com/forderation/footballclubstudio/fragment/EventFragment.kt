package com.forderation.footballclubstudio.fragment

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
import com.forderation.footballclubstudio.model.event.Event
import kotlinx.android.synthetic.main.fragment_event_list.*

class EventFragment : Fragment(),
    ListEventView {

    companion object{
        const val LATEST_MATCH = 0
        const val UPCOMING_MATCH = 1
        const val FAV_MATCH = 2
        const val ID_LEAGUE = "ID_LEAGUE"
        const val TYPE_EVENT = "TYPE_EVENT"
        fun newInstance(idLeague:String,typeEvent:Int):EventFragment{
            val bundle = Bundle()
            bundle.putString(ID_LEAGUE,idLeague)
            bundle.putInt(TYPE_EVENT, typeEvent)
            val fg = EventFragment()
            fg.arguments = bundle
            return fg
        }
    }

    override fun inflateListEvent(listEvent: List<Event>) {
        mAdapter = EventAdapter(listEvent){ e ->
            val intent = Intent(activity,EventDetailActivity::class.java)
            intent.putExtra(EventDetailActivity.EVENT_INTENT, e)
            startActivity(intent)
        }
        list_event.layoutManager = LinearLayoutManager(context)
        list_event.adapter = mAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    private lateinit var mPresenter: EventPresenter
    private lateinit var mAdapter: EventAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_event.layoutManager = LinearLayoutManager(context)
        val bundle = arguments
        if(bundle!=null){
            mPresenter = EventPresenter(this, bundle.getInt(TYPE_EVENT), activity!!.applicationContext)
            mPresenter.getListEventLatestMatch(bundle.getString(ID_LEAGUE)!!)
        }
    }
}

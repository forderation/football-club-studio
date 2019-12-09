package com.forderation.footballclubstudio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.adapter.ClubAdapter
import com.forderation.footballclubstudio.adapter.EventAdapter
import kotlinx.android.synthetic.main.fragment_rv.*

class ResultSearchUnderLeague :Fragment(){
    var eventAdapter: EventAdapter? = null
    var teamAdapter: ClubAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rv,container,false)
    }

    companion object{
        private const val KEY_STATE = "KEY_STATE"
        fun newInstance(state: String):ResultSearchUnderLeague{
            val bundle = Bundle()
            bundle.putString(KEY_STATE,state)
            val fg = ResultSearchUnderLeague()
            fg.arguments = bundle
            return fg
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        if(bundle!=null){
            when(bundle.getString(KEY_STATE)){
                UnderLeagueFragment.STATE_LIST_EVENT -> {
                    list_item.layoutManager = LinearLayoutManager(context)
                    list_item.adapter = eventAdapter
                }
                UnderLeagueFragment.STATE_LIST_TEAM -> {
                    list_item.layoutManager = GridLayoutManager(context,3)
                    list_item.adapter = teamAdapter
                }
            }
        }
    }
}
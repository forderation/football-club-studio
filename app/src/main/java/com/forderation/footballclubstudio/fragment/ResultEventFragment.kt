package com.forderation.footballclubstudio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.adapter.EventAdapter
import kotlinx.android.synthetic.main.fragment_under_league_list.*

class ResultEventFragment :Fragment(){
    var adapter: EventAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_under_league_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_item.layoutManager = LinearLayoutManager(context)
        list_item.adapter = adapter
    }
}
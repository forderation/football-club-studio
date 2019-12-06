package com.forderation.footballclubstudio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.adapter.ClubAdapter
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.club.GetTeams
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_under_league_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListClubFragment: Fragment(){

    companion object{
        const val NAME_LEAGUE = "NAME_LEAGUE"
        fun newInstance(nameLeague:String):ListClubFragment{
            val bundle = Bundle()
            bundle.putString(NAME_LEAGUE,nameLeague)
            val fg = ListClubFragment()
            fg.arguments = bundle
            return fg
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_under_league_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_item.layoutManager = GridLayoutManager(context,3)
        val bundle = arguments
        if (bundle != null) {
            val idLeague = bundle.getString(NAME_LEAGUE)
            GlobalScope.launch(Dispatchers.Main) {
                val resp = Gson().fromJson(
                    ApiClient().doRequestAsync(Endpoints.getListClub(idLeague.toString())).await(),
                    GetTeams::class.java
                )
                if(list_item!=null){
                    val adapter = ClubAdapter {}
                    if(resp.clubs!= null){
                        adapter.clubList = resp.clubs.toMutableList()
                        list_item.adapter = adapter
                    }
                }
            }
        }
    }
}
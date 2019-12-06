package com.forderation.footballclubstudio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.adapter.ClassementAdapter
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.classement.GetClassement
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_under_league_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ClassementFragment: Fragment(){

    companion object{
        const val ID_LEAGUE = "ID_LEAGUE"
        fun newInstance(idLeague:Int):ClassementFragment{
            val bundle = Bundle()
            bundle.putInt(ID_LEAGUE,idLeague)
            val fg = ClassementFragment()
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
        list_item.layoutManager = LinearLayoutManager(context)
        val bundle = arguments
        if (bundle != null) {
            val idLeague = bundle.getInt(ID_LEAGUE,0)
            GlobalScope.launch(Dispatchers.Main) {
                val resp = Gson().fromJson(
                    ApiClient().doRequestAsync(Endpoints.getClassement(idLeague.toString())).await(), GetClassement::class.java
                )
                if(list_item!=null){
                    if(resp!=null){
                        if(resp.list!=null){
                            list_item.adapter = ClassementAdapter(resp.list){
                            }
                        }
                    }
                }
            }
        }
    }
}
package com.forderation.footballclubstudio.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.fragment.EventFragment
import com.forderation.footballclubstudio.fragment.ListFavFragment
import com.forderation.footballclubstudio.fragment.ListLeagueFragment
import com.fxn.OnBubbleClickListener
import kotlinx.android.synthetic.main.activity_leagues.*

class LeaguesActivity : AppCompatActivity() {

    private lateinit var leaguesFg: ListLeagueFragment
    private lateinit var favEventFg: ListFavFragment
    private fun showFgLeagues(item:Int = 10){
        leaguesFg = ListLeagueFragment.newInstance(item)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_container, leaguesFg, ListLeagueFragment::class.java.simpleName)
            .commit()
    }

    private fun showFgFavEvent(){
        favEventFg = ListFavFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_container, favEventFg, ListFavFragment::class.java.simpleName)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leagues)
        showFgLeagues()
        tab_item_fav.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                if(currentItemId != null && id == currentItemId) return
                when(id){
                    R.id.item_10 -> {
                        showFgLeagues()
                    }
                    R.id.item_30 -> {
                        showFgLeagues(30)
                    }
                    R.id.item_60 -> {
                        showFgLeagues(60)
                    }
                    R.id.item_fav -> {
                        showFgFavEvent()
                    }
                }
                currentItemId = id
            }
        })
    }
    private var currentItemId:Int? = null
}

package com.forderation.footballclubstudio.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.fragment.ListLeagueFragment
import com.fxn.OnBubbleClickListener
import kotlinx.android.synthetic.main.activity_leagues.*

class LeaguesActivity : AppCompatActivity() {

    private lateinit var leaguesFg: ListLeagueFragment

    private fun showFgLeagues(item:Int = 10){
        leaguesFg = ListLeagueFragment.newInstance(this, item)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_container, leaguesFg, ListLeagueFragment::class.java.simpleName)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leagues)
        showFgLeagues()
        tab_item_menu.addBubbleListener(object : OnBubbleClickListener {
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
                }
                currentItemId = id
            }
        })
    }
    private var currentItemId:Int? = null
}

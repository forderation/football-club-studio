package com.forderation.footballclubstudio.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.fragment.ListLeagueFragment
import com.fxn.OnBubbleClickListener
import kotlinx.android.synthetic.main.activity_leagues.*

class LeaguesActivity : AppCompatActivity() {

    private lateinit var leaguesFg: ListLeagueFragment

    private fun showFgLeagues(){
        leaguesFg = ListLeagueFragment.newInstance(this)
        val fragment = supportFragmentManager.findFragmentByTag(ListLeagueFragment::class.java.simpleName)
        if (fragment !is ListLeagueFragment){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, leaguesFg, ListLeagueFragment::class.java.simpleName)
                .commit()
        }
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
                        leaguesFg.setLimitItem(10)
                    }
                    R.id.item_30 -> {
                        leaguesFg.setLimitItem(30)
                    }
                    R.id.item_60 -> {
                        leaguesFg.setLimitItem(60)
                    }
                }
                currentItemId = id
            }
        })
    }
    private var currentItemId:Int? = null
}

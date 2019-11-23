package com.forderation.footballclubstudio.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.model.event.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*

class EventDetailActivity : AppCompatActivity() {



    companion object{
        const val EVENT_INTENT = "EVENT_INTENT"
    }

    private lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        event = intent.getParcelableExtra(EVENT_INTENT)!!
        title_event.text = event.name
        time_event.text = event.time
        date_event.text = event.date
        score_home.text = event.homeScore
        score_away.text = event.awayScore
        round_league.text = getString(R.string.round_tv).plus(event.round)
        name_league.text = event.strLeague
        home_team.text = event.homeTeam
        away_team.text = event.awayTeam
        //home attributes
        goal_details_home.setText(event.strHomeGoalDetails,this)
        red_cards_home.setText(event.strHomeRedCards,this)
        yellow_card_home.setText(event.strHomeYellowCards,this)
        gk_home.setText(event.strHomeLineupGoalkeeper,this)
        defenser_home.setText(event.strHomeLineupDefense,this)
        mildfielder_home.setText(event.strHomeLineupMidfield,this)
        fordwarder_home.setText(event.strHomeLineupForward,this)
        subtitues_home.setText(event.strHomeLineupSubstitutes,this)
        //away attributes
        goal_details_away.setText(event.strAwayGoalDetails,this)
        red_cards_away.setText(event.strAwayRedCards,this)
        yellow_card_away.setText(event.strAwayYellowCards,this)
        gk_away.setText(event.strAwayLineupGoalkeeper,this)
        defenser_away.setText(event.strAwayLineupDefense,this)
        midfielder_away.setText(event.strAwayLineupMidfield,this)
        forwarder_away.setText(event.strAwayLineupForward,this)
        subtitues_away.setText(event.strAwayLineupSubstitutes,this)
        supportActionBar?.title = "Match Detail"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}

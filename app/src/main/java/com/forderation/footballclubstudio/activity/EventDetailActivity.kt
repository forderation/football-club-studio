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
        const val HOME_BADGE = "HOME_BADGE"
        const val AWAY_BADGE = "AWAY_BADGE"
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
        goal_details_home.text = event.strHomeGoalDetails
        red_cards_home.text = event.strHomeRedCards
        yellow_card_home.text = event.strHomeYellowCards
        gk_home.text = event.strHomeLineupGoalkeeper
        defenser_home.text = event.strHomeLineupDefense
        mildfielder_home.text = event.strHomeLineupMidfield
        fordwarder_home.text = event.strHomeLineupForward
        subtitues_home.text = event.strHomeLineupSubstitutes
        goal_details_away.text = event.strAwayGoalDetails
        red_cards_away.text = event.strAwayRedCards
        yellow_card_away.text = event.strAwayYellowCards
        home_team.text = event.homeTeam
        away_team.text = event.awayTeam
        gk_away.text = event.strAwayLineupGoalkeeper
        defenser_away.text = event.strAwayLineupDefense
        mildfielder_away.text = event.strAwayLineupMidfield
        fordwarder_away.text = event.strAwayLineupForward
        subtitues_away.text = event.strAwayLineupSubstitutes
        Picasso.get().load(intent.getStringExtra(HOME_BADGE)).fit().centerInside().into(home_badge)
        Picasso.get().load(intent.getStringExtra(AWAY_BADGE)).fit().centerInside().into(away_badge)
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

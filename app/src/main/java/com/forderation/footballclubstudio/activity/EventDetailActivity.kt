package com.forderation.footballclubstudio.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.presenter.DetailEventPresenter
import com.forderation.footballclubstudio.activity.view.DetailEventView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.model.event.Event
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.jetbrains.anko.design.longSnackbar

class EventDetailActivity : AppCompatActivity(),DetailEventView {

    companion object {
        const val ID_EVENT = "ID_EVENT"
        const val HOME_BADGE_URL = "HOME_BADGE_URL"
        const val AWAY_BADGE_URL = "AWAY_BADGE_URL"
        const val IS_FAV_CHANGE = "IS_FAV_CHANGE"
    }

    private var isFav: Boolean = false
    private lateinit var id: String
    private var favMenu: MenuItem? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_match, menu)
        favMenu = menu?.getItem(0)
        updateIcon()
        return true
    }

    private fun updateIcon() {
        if (isFav) {
            favMenu?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        } else {
            favMenu?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
        }
    }

    private lateinit var homeImgUrl: String
    private lateinit var awayImgUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        id = intent.getStringExtra(ID_EVENT)!!
        homeImgUrl = intent.getStringExtra(HOME_BADGE_URL)!!
        awayImgUrl = intent.getStringExtra(AWAY_BADGE_URL)!!
        if (homeImgUrl.isNotEmpty() && awayImgUrl.isNotEmpty()) {
            Picasso.get()
                .load(homeImgUrl)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.image_failed)
                .into(home_badge)
            Picasso.get()
                .load(awayImgUrl)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.image_failed)
                .into(away_badge)
        }
        presenter = DetailEventPresenter(this,Gson(), ApiClient())
        presenter.context = this
        presenter.getDetailEvent(id)
        supportActionBar?.title = "Match Detail"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        isFav = presenter.checkFav(id)
    }

    private lateinit var presenter:DetailEventPresenter

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                true
            }
            R.id.fav_match_item -> {
                if (!isFav) {
                    presenter.addToFav(mEvent, id, homeImgUrl, awayImgUrl)
                    isFav = true
                    updateIcon()
                } else {
                    presenter.removeFav(id)
                    isFav = false
                    updateIcon()
                }
                setResult(Activity.RESULT_OK, Intent().putExtra(IS_FAV_CHANGE, true))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    private lateinit var mEvent:Event

    override fun showDetailEvent(listEvent: List<Event>) {
        this.mEvent = listEvent[0]
        title_event.text = mEvent.name
        time_event.text = mEvent.time
        date_event.text = mEvent.date
        score_home.text = mEvent.homeScore
        score_away.text = mEvent.awayScore
        round_league.text = getString(R.string.round_tv).plus(mEvent.round)
        name_league.text = mEvent.strLeague
        home_team.text = mEvent.homeTeam
        away_team.text = mEvent.awayTeam
        //home attributes
        goal_details_home.setText(mEvent.strHomeGoalDetails, this)
        red_cards_home.setText(mEvent.strHomeRedCards, this)
        yellow_card_home.setText(mEvent.strHomeYellowCards, this)
        gk_home.setText(mEvent.strHomeLineupGoalkeeper, this)
        defender_home.setText(mEvent.strHomeLineupDefense, this)
        midfielder_home.setText(mEvent.strHomeLineupMidfield, this)
        forwarder_home.setText(mEvent.strHomeLineupForward, this)
        substitutes_home.setText(mEvent.strHomeLineupSubstitutes, this)
        formation_home.setText(mEvent.strHomeFormation, this)
        //away attributes
        goal_details_away.setText(mEvent.strAwayGoalDetails, this)
        red_cards_away.setText(mEvent.strAwayRedCards, this)
        yellow_card_away.setText(mEvent.strAwayYellowCards, this)
        gk_away.setText(mEvent.strAwayLineupGoalkeeper, this)
        defender_away.setText(mEvent.strAwayLineupDefense, this)
        midfielder_away.setText(mEvent.strAwayLineupMidfield, this)
        forwarder_away.setText(mEvent.strAwayLineupForward, this)
        substitutes_away.setText(mEvent.strAwayLineupSubstitutes, this)
        formation_away.setText(mEvent.strAwayFormation, this)
    }

    override fun showMsg(msg: String) {
        root_layout.longSnackbar(msg).show()
    }
}

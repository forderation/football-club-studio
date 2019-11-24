package com.forderation.footballclubstudio.activity

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.db.FavEvent
import com.forderation.footballclubstudio.db.database
import com.forderation.footballclubstudio.model.event.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.longSnackbar

class EventDetailActivity : AppCompatActivity() {

    companion object {
        const val EVENT_INTENT = "EVENT_INTENT"
        const val HOME_BADGE_URL = "HOME_BADGE_URL"
        const val AWAY_BADGE_URL = "AWAY_BADGE_URL"
        const val IS_FAV_CHANGE = "IS_FAV_CHANGE"
    }

    private lateinit var mEvent: Event
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
        mEvent = intent.getParcelableExtra(EVENT_INTENT)!!
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
        id = mEvent.idEvent!!
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
        supportActionBar?.title = "Match Detail"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        checkFavourite()
    }

    private fun checkFavourite() {
        database.use {
            val result = select(FavEvent.TABLE_FAV_EVENT)
                .whereArgs(
                    "(IdEvent = {idEvent})",
                    "idEvent" to id
                )
            val favourites = result.parseList(classParser<FavEvent>())
            isFav = favourites.isNotEmpty()
        }
    }

    private fun makeAsFav() {
        try {
            database.use {
                insert(
                    FavEvent.TABLE_FAV_EVENT,
                    FavEvent.IdLeague to mEvent.idLeague,
                    FavEvent.IdEvent to id,
                    FavEvent.Name to mEvent.name,
                    FavEvent.Time to mEvent.time,
                    FavEvent.Date to mEvent.date,
                    FavEvent.HomeScore to mEvent.homeScore,
                    FavEvent.AwayScore to mEvent.awayScore,
                    FavEvent.Round to mEvent.round,
                    FavEvent.League to mEvent.strLeague,
                    FavEvent.HomeTeam to mEvent.homeTeam,
                    FavEvent.AwayTeam to mEvent.awayTeam,
                    FavEvent.HomeGoalDetails to mEvent.strHomeGoalDetails,
                    FavEvent.HomeRedCards to mEvent.strHomeRedCards,
                    FavEvent.HomeYellowCards to mEvent.strHomeYellowCards,
                    FavEvent.HomeLineupGoalkeeper to mEvent.strHomeLineupGoalkeeper,
                    FavEvent.HomeLineupDefense to mEvent.strHomeLineupDefense,
                    FavEvent.HomeLineupMidfield to mEvent.strHomeLineupMidfield,
                    FavEvent.HomeLineupForward to mEvent.strHomeLineupForward,
                    FavEvent.HomeLineupSubstitutes to mEvent.strHomeLineupSubstitutes,
                    FavEvent.AwayGoalDetails to mEvent.strAwayGoalDetails,
                    FavEvent.AwayRedCards to mEvent.strAwayRedCards,
                    FavEvent.AwayYellowCards to mEvent.strAwayYellowCards,
                    FavEvent.AwayLineupGoalkeeper to mEvent.strAwayLineupGoalkeeper,
                    FavEvent.AwayLineupDefense to mEvent.strAwayLineupDefense,
                    FavEvent.AwayLineupMidfield to mEvent.strAwayLineupMidfield,
                    FavEvent.AwayLineupForward to mEvent.strAwayLineupForward,
                    FavEvent.AwayLineupSubstitutes to mEvent.strAwayLineupSubstitutes,
                    FavEvent.HomeBadge to homeImgUrl,
                    FavEvent.AwayBadge to awayImgUrl,
                    FavEvent.HomeFormation to mEvent.strHomeFormation,
                    FavEvent.AwayFormation to mEvent.strAwayFormation,
                    FavEvent.IdHome to mEvent.idHome,
                    FavEvent.IdAway to mEvent.idAway
                )
            }
            root_layout.longSnackbar(getString(R.string.fav_added)).show()
            isFav = true
            updateIcon()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                true
            }
            R.id.fav_match_item -> {
                if (!isFav) {
                    makeAsFav()
                } else {
                    removeFromFav()
                }
                setResult(Activity.RESULT_OK, Intent().putExtra(IS_FAV_CHANGE, true))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun removeFromFav() {
        try {
            database.use {
                delete(FavEvent.TABLE_FAV_EVENT, "${FavEvent.IdEvent} = $id")
            }
            root_layout.longSnackbar(getString(R.string.fav_removed)).show()
            isFav = false
            updateIcon()
        } catch (e: SQLiteConstraintException) {
            root_layout.longSnackbar(e.toString()).show()
        }
    }
}

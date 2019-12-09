package com.forderation.footballclubstudio.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class FavDBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "fcs_database.db", version = 1) {

    companion object {
        private var instance: FavDBHelper? = null
        @Synchronized
        fun getInstance(ctx: Context): FavDBHelper {
            if (instance == null) {
                instance = FavDBHelper(ctx.applicationContext)
            }
            return instance as FavDBHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            FavEvent.TABLE_FAV_EVENT, true,
            FavEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavEvent.IdEvent to TEXT,
            FavEvent.IdLeague to TEXT,
            FavEvent.Round to TEXT,
            FavEvent.Name to TEXT,
            FavEvent.Sport to TEXT,
            FavEvent.Date to TEXT,
            FavEvent.League to TEXT,
            FavEvent.Time to TEXT,
            FavEvent.IdHome to TEXT,
            FavEvent.IdAway to TEXT,
            FavEvent.HomeTeam to TEXT,
            FavEvent.AwayTeam to TEXT,
            FavEvent.HomeScore to TEXT,
            FavEvent.AwayScore to TEXT,
            FavEvent.Thumbnail to TEXT,
            FavEvent.HomeGoalDetails to TEXT,
            FavEvent.HomeRedCards to TEXT,
            FavEvent.HomeYellowCards to TEXT,
            FavEvent.HomeLineupGoalkeeper to TEXT,
            FavEvent.HomeLineupDefense to TEXT,
            FavEvent.HomeLineupMidfield to TEXT,
            FavEvent.HomeLineupForward to TEXT,
            FavEvent.HomeLineupSubstitutes to TEXT,
            FavEvent.HomeFormation to TEXT,
            FavEvent.AwayGoalDetails to TEXT,
            FavEvent.AwayRedCards to TEXT,
            FavEvent.AwayYellowCards to TEXT,
            FavEvent.AwayLineupGoalkeeper to TEXT,
            FavEvent.AwayLineupDefense to TEXT,
            FavEvent.AwayLineupMidfield to TEXT,
            FavEvent.AwayLineupForward to TEXT,
            FavEvent.AwayLineupSubstitutes to TEXT,
            FavEvent.AwayFormation to TEXT,
            FavEvent.HomeBadge to TEXT,
            FavEvent.AwayBadge to TEXT
        )
        db.createTable(
            FavClub.TABLE_FAV_CLUB, true,
            FavClub.id to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavClub.idTeam to TEXT,
            FavClub.idLeague to TEXT,
            FavClub.name to TEXT,
            FavClub.badge to TEXT,
            FavClub.strStadium to TEXT,
            FavClub.strStadiumThumb to TEXT,
            FavClub.strStadiumDescription to TEXT,
            FavClub.strStadiumLocation to TEXT,
            FavClub.strDescriptionEN to TEXT,
            FavClub.strTeamJersey to TEXT,
            FavClub.strTeamFanart1 to TEXT,
            FavClub.strTeamFanart2 to TEXT,
            FavClub.strTeamFanart3 to TEXT,
            FavClub.strTeamFanart4 to TEXT,
            FavClub.intFormedYear to TEXT,
            FavClub.strWebsite to TEXT,
            FavClub.strFacebook to TEXT,
            FavClub.strTwitter to TEXT,
            FavClub.strInstagram to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavEvent.TABLE_FAV_EVENT, true)
        db.dropTable(FavClub.TABLE_FAV_CLUB, true)
    }
}

val Context.database: FavDBHelper
    get() = FavDBHelper.getInstance(applicationContext)
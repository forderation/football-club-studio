package com.forderation.footballclubstudio.database

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
            FavEventContracts.TABLE_FAV_EVENT, true,
            FavEventContracts.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavEventContracts.IdLeague to TEXT + UNIQUE,
            FavEventContracts.ROUND to TEXT,
            FavEventContracts.Name to TEXT,
            FavEventContracts.Sport to TEXT,
            FavEventContracts.Date to TEXT,
            FavEventContracts.League to TEXT,
            FavEventContracts.Time to TEXT,
            FavEventContracts.IdHome to TEXT,
            FavEventContracts.IdAway to TEXT,
            FavEventContracts.HomeTeam to TEXT,
            FavEventContracts.AwayTeam to TEXT,
            FavEventContracts.HomeScore to TEXT,
            FavEventContracts.AwayScore to TEXT,
            FavEventContracts.Thumbnail to TEXT,
            FavEventContracts.HomeGoalDetails to TEXT,
            FavEventContracts.HomeRedCards to TEXT,
            FavEventContracts.HomeYellowCards to TEXT,
            FavEventContracts.HomeLineupGoalkeeper to TEXT,
            FavEventContracts.HomeLineupDefense to TEXT,
            FavEventContracts.HomeLineupMidfield to TEXT,
            FavEventContracts.HomeLineupForward to TEXT,
            FavEventContracts.HomeLineupSubstitutes to TEXT,
            FavEventContracts.HomeFormation to TEXT,
            FavEventContracts.AwayGoalDetails to TEXT,
            FavEventContracts.AwayRedCards to TEXT,
            FavEventContracts.AwayYellowCards to TEXT,
            FavEventContracts.AwayLineupGoalkeeper to TEXT,
            FavEventContracts.AwayLineupDefense to TEXT,
            FavEventContracts.AwayLineupMidfield to TEXT,
            FavEventContracts.AwayLineupForward to TEXT,
            FavEventContracts.AwayLineupSubstitutes to TEXT,
            FavEventContracts.AwayFormation to TEXT,
            FavEventContracts.HomeBadge to BLOB,
            FavEventContracts.AwayBadge to BLOB
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavEventContracts.TABLE_FAV_EVENT, true)
    }

    val Context.database: FavDBHelper
        get() = FavDBHelper.getInstance(applicationContext)
}
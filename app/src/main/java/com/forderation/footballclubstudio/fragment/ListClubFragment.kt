package com.forderation.footballclubstudio.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.ClubDetailActivity
import com.forderation.footballclubstudio.activity.EventDetailActivity
import com.forderation.footballclubstudio.activity.presenter.ClubPresenter
import com.forderation.footballclubstudio.activity.view.ListClubView
import com.forderation.footballclubstudio.adapter.ClubAdapter
import com.forderation.footballclubstudio.db.FavClub
import com.forderation.footballclubstudio.db.database
import com.forderation.footballclubstudio.db.toClub
import com.forderation.footballclubstudio.model.club.Club
import kotlinx.android.synthetic.main.fragment_rv.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class ListClubFragment : Fragment(), ListClubView {

    companion object {
        const val NAME_LEAGUE = "NAME_LEAGUE"
        private const val TYPE_LIST_CLUB = "TYPE_LIST_CLUB"
        const val REQUEST_UPDATE_FAV = 4
        const val FAV_LIST_CLUB = "FAV_LIST_CLUB"
        const val NET_LIST_CLUB = "NET_LIST_CLUB"

        fun newInstance(nameLeague: String, type: String): ListClubFragment {
            val bundle = Bundle()
            bundle.putString(NAME_LEAGUE, nameLeague)
            bundle.putString(TYPE_LIST_CLUB, type)
            val fg = ListClubFragment()
            fg.arguments = bundle
            return fg
        }

        fun newInstanceByDB(type: String): ListClubFragment {
            val bundle = Bundle()
            bundle.putString(TYPE_LIST_CLUB, type)
            val fg = ListClubFragment()
            fg.arguments = bundle
            return fg
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_item.layoutManager = GridLayoutManager(context, 3)
        val presenter = ClubPresenter(this)
        presenter.context = context!!
        val bundle = arguments
        if (bundle != null) {
            val type = bundle.getString(TYPE_LIST_CLUB)
            if (type == NET_LIST_CLUB) {
                val nameLeague = bundle.getString(NAME_LEAGUE)
                presenter.getNetListClub(nameLeague!!)
            } else if (type == FAV_LIST_CLUB) {
                inflateEventFav()
            }
        }
    }

    override fun inflateListClub(listClub: List<Club>) {
        if (list_item != null) {
            val adapter = ClubAdapter {
                val intent = Intent(activity, ClubDetailActivity::class.java)
                intent.putExtra(ClubDetailActivity.CLUB_ID, it.idTeam)
                startActivity(intent)
            }
            adapter.clubList = listClub.toMutableList()
            list_item.adapter = adapter
        }
    }

    private lateinit var mAdapterFav: ClubAdapter

    private fun inflateEventFav() {
        var favorites: List<FavClub> = arrayListOf()
        context?.database?.use {
            val result = select(FavClub.TABLE_FAV_CLUB)
            favorites = result.parseList(classParser())
        }
        val listClub: MutableList<Club> = arrayListOf()
        mAdapterFav = ClubAdapter { e ->
            val intent = Intent(activity, ClubDetailActivity::class.java)
            intent.putExtra(ClubDetailActivity.CLUB_ID, e.idTeam)
            startActivityForResult(intent, REQUEST_UPDATE_FAV)
        }
        favorites.forEach {
            listClub.add(it.toClub())
        }
        mAdapterFav.clubList = listClub
        list_item.layoutManager = GridLayoutManager(context, 3)
        list_item.adapter = mAdapterFav
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_UPDATE_FAV) {
            if (resultCode == Activity.RESULT_OK) {
                if (data?.getBooleanExtra(ClubDetailActivity.IS_FAV_CHANGE, false) == true) {
                    inflateEventFav()
                }
            }
        }
    }
}
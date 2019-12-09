package com.forderation.footballclubstudio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.adapter.PagerAdapter
import com.fxn.OnBubbleClickListener
import kotlinx.android.synthetic.main.fragment_fav.*

class ListFavFragment  : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerAdapter = PagerAdapter(childFragmentManager)
        pagerAdapter.listFragment.add(EventFragment.newInstance(EventFragment.FAV_MATCH))
        pagerAdapter.listFragment.add(ListClubFragment.newInstanceByDB(ListClubFragment.FAV_LIST_CLUB))
        view_pager_fav.adapter = pagerAdapter
        view_pager_fav.offscreenPageLimit = 2
        pagerAdapter.notifyDataSetChanged()
        tab_item_fav.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                when (id) {
                    R.id.item_event_fav -> {
                        view_pager_fav.currentItem = 0
                    }
                    R.id.item_club_fav -> {
                        view_pager_fav.currentItem = 1
                    }
                }
            }
        })
        tab_item_fav.setupBubbleTabBar(view_pager_fav)
    }

}
package com.forderation.footballclubstudio.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.adapter.PagerAdapter
import com.forderation.footballclubstudio.model.club.Club
import com.fxn.OnBubbleClickListener
import kotlinx.android.synthetic.main.fragment_next_last_match.*

class NextLastFragment(private var clubList:List<Club>, private var leagueId:String) : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_next_last_match,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerAdapter = PagerAdapter(childFragmentManager)
        pagerAdapter.listFragment.add(EventFragment(leagueId,clubList,EventFragment.LATEST_MATCH))
        pagerAdapter.listFragment.add(EventFragment(leagueId,clubList,EventFragment.UPCOMING_MATCH))
        view_pager_event.adapter = pagerAdapter
        view_pager_event.offscreenPageLimit = 2
        pagerAdapter.notifyDataSetChanged()
        tab_event_menu.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                when (id) {
                    R.id.last_match_tab -> {
                        view_pager_event.currentItem = EventFragment.LATEST_MATCH
                    }
                    R.id.next_match_tab -> {
                        view_pager_event.currentItem = EventFragment.UPCOMING_MATCH
                    }
                }
            }
        })
        tab_event_menu.setupBubbleTabBar(view_pager_event)
    }
}
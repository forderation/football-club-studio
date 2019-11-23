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

class NextLastFragment : Fragment(){

    companion object{
        private const val LEAGUE_ID = "LEAGUE_ID"
        fun newInstance(leagueId:String): NextLastFragment{
            val args = Bundle()
            args.putString(LEAGUE_ID,leagueId)
            val fg = NextLastFragment()
            fg.arguments = args
            return fg
        }
    }

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
        val bundle = arguments
        if (bundle != null){
            val lgId = bundle.getString(LEAGUE_ID)!!
            pagerAdapter.listFragment.add(EventFragment.newInstance(lgId, EventFragment.LATEST_MATCH))
            pagerAdapter.listFragment.add(EventFragment.newInstance(lgId, EventFragment.UPCOMING_MATCH))
        }
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
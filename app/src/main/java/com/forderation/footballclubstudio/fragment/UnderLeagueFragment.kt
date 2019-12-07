package com.forderation.footballclubstudio.fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.view.DetailLeagueView
import com.forderation.footballclubstudio.adapter.PagerAdapter
import com.fxn.OnBubbleClickListener
import kotlinx.android.synthetic.main.fragment_under_league.*
import org.jetbrains.anko.support.v4.onPageChangeListener

class UnderLeagueFragment : Fragment(){
    private lateinit var detailLeagueView: DetailLeagueView
    companion object{
        const val STATE_LIST_TEAM = "STATE_LIST_TEAM"
        const val STATE_LIST_EVENT = "STATE_LIST_EVENT"
        private const val LEAGUE_ID = "LEAGUE_ID"
        private const val NAME_LEAGUE = "NAME_LEAGUE"
        fun newInstance(leagueId:String, nameLeague:String): UnderLeagueFragment{
            val args = Bundle()
            args.putString(LEAGUE_ID,leagueId)
            args.putString(NAME_LEAGUE,nameLeague)
            val fg = UnderLeagueFragment()
            fg.arguments = args
            return fg
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_under_league,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerAdapter = PagerAdapter(childFragmentManager)
        val bundle = arguments
        if (bundle != null){
            val lgId = bundle.getString(LEAGUE_ID)!!
            val nameLg = bundle.getString(NAME_LEAGUE)
            pagerAdapter.listFragment.add(ClassementFragment.newInstance(lgId.toInt()))
            pagerAdapter.listFragment.add(ListClubFragment.newInstance(nameLg!!))
            pagerAdapter.listFragment.add(EventFragment.newInstance(lgId, EventFragment.LATEST_MATCH))
            pagerAdapter.listFragment.add(EventFragment.newInstance(lgId, EventFragment.NEXT_MATCH))
        }
        detailLeagueView = activity as DetailLeagueView
        view_pager_menu.adapter = pagerAdapter
        view_pager_menu.offscreenPageLimit = 4
        pagerAdapter.notifyDataSetChanged()
        tab_menu.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                when (id) {
                    R.id.classement_tab -> {
                        view_pager_menu.currentItem = 0
                        detailLeagueView.setVisibilitySearch(false)
                    }
                    R.id.list_team_tab -> {
                        view_pager_menu.currentItem = 1
                        detailLeagueView.setVisibilitySearch(true)
                        detailLeagueView.setCurrentStateSearch(STATE_LIST_TEAM)
                    }
                    R.id.last_match_tab -> {
                        view_pager_menu.currentItem = 2
                        detailLeagueView.setVisibilitySearch(false)
                    }
                    R.id.next_match_tab -> {
                        view_pager_menu.currentItem = 3
                        detailLeagueView.setVisibilitySearch(true)
                        detailLeagueView.setCurrentStateSearch(STATE_LIST_EVENT)
                    }
                }
            }
        })
        tab_menu.setupBubbleTabBar(view_pager_menu)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        tab_menu.setSelected(0)
        view_pager_menu.currentItem = 0
        tab_menu[1].isActivated = false
        tab_menu[2].isActivated = false
        tab_menu[3].isActivated = false
    }
}
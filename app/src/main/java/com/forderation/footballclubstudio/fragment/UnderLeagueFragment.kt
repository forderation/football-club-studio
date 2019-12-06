package com.forderation.footballclubstudio.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.adapter.PagerAdapter
import com.fxn.OnBubbleClickListener
import kotlinx.android.synthetic.main.fragment_under_league.*

class UnderLeagueFragment : Fragment(){

    companion object{
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
        view_pager_menu.adapter = pagerAdapter
        view_pager_menu.offscreenPageLimit = 4
        pagerAdapter.notifyDataSetChanged()
        tab_menu.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                when (id) {
                    R.id.classement_tab -> {
                        view_pager_menu.currentItem = 0
                    }
                    R.id.list_team_tab -> {
                        view_pager_menu.currentItem = 1
                    }
                    R.id.last_match_tab -> {
                        view_pager_menu.currentItem = 2
                    }
                    R.id.next_match_tab -> {
                        view_pager_menu.currentItem = 3
                    }
                }
            }
        })
        tab_menu.setupBubbleTabBar(view_pager_menu)
    }
}
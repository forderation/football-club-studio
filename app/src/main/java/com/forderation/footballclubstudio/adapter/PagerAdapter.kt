package com.forderation.footballclubstudio.adapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var listFragment: MutableList<Fragment> = arrayListOf()

    override fun getItem(position: Int): Fragment {
        return this.listFragment[position]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

}
package com.forderation.footballclubstudio.ui

import androidx.recyclerview.widget.GridLayoutManager
import com.forderation.footballclubstudio.ClubListActivity
import com.forderation.footballclubstudio.adapter.ClubAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class ClubListUI(private val mAdapter: ClubAdapter): AnkoComponent<ClubListActivity>{

    override fun createView(ui: AnkoContext<ClubListActivity>) = with(ui){
        relativeLayout{
            lparams(matchParent, wrapContent)
            padding = dip(16)
            recyclerView {
                lparams(width= matchParent, height = wrapContent)
                layoutManager = GridLayoutManager(ctx,2)
                adapter = mAdapter
            }
        }
    }

}
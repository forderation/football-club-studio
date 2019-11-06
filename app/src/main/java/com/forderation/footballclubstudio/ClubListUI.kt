package com.forderation.footballclubstudio

import androidx.recyclerview.widget.GridLayoutManager
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class ClubListUI(private val mAdapter:ClubAdapter): AnkoComponent<MainActivity>{

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui){
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
package com.forderation.footballclubstudio

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class ClubItemUI :AnkoComponent<ViewGroup>{

    companion object{
        const val clubImg = 1
        const val clubName = 2
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View {
        with(ui){
            return verticalLayout {
                orientation = LinearLayout.VERTICAL
                cardView {
                    radius = dip(10).toFloat()
                    imageView {
                        id = clubImg
                    }.lparams(dip(50), dip(50)){
                        setMargins(10,10,10,10)
                    }
                }.lparams(matchParent, wrapContent)
                textView{
                    id = clubName
                    textSize = sp(8).toFloat()
                    textColor = Color.BLACK
                }.lparams(wrapContent, wrapContent){
                    gravity = Gravity.CENTER_HORIZONTAL
                    margin = dip(5)
                }
            }
        }
    }
}

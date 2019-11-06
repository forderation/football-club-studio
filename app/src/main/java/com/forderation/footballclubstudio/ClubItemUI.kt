package com.forderation.footballclubstudio

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class ClubItemUI :AnkoComponent<ViewGroup> {

    companion object {
        const val clubImg = 1
        const val clubName = 2
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View {
        with(ui) {
            return linearLayout {
                orientation = LinearLayout.VERTICAL
                cardView{
                    verticalLayout {
                        orientation = LinearLayout.VERTICAL
                        imageView {
                            id = clubImg
                        }.lparams(dip(100), dip(100)){
                            setMargins(0,30,0,20)
                            gravity = Gravity.CENTER_HORIZONTAL
                        }
                        textView{
                            id = clubName
                            textAlignment = View.TEXT_ALIGNMENT_CENTER
                            textColor = Color.BLACK
                            textSize = sp(6).toFloat()
                        }.lparams(wrapContent, wrapContent){
                            setMargins(30,10,30,10)
                        }
                    }.lparams(matchParent, wrapContent)
                    radius = dip(10).toFloat()
                }.lparams(matchParent, 600){
                    setMargins(20,20,20,20)
                }
            }
        }
    }
}

package com.forderation.footballclubstudio.ui

import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.forderation.footballclubstudio.ClubDetailActivity
import com.forderation.footballclubstudio.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

@Suppress("DEPRECATION")
class ClubDetailUI : AnkoComponent<ClubDetailActivity>{

    companion object{
        const val clubLogo = 1
        const val clubTitle = 2
        const val clubDesc = 3
    }

    override fun createView(ui: AnkoContext<ClubDetailActivity>): View {
        with(ui){
            return scrollView {
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    relativeLayout {
                        frameLayout {
                            imageView {
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                Picasso.get().load(R.drawable.banner_football).fit().into(this)
                            }.lparams(width = matchParent, height = matchParent)
                        }.lparams(width = matchParent, height = dip(200))
                        frameLayout {
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                cardView {
                                    radius = dip(10).toFloat()
                                    imageView {
                                        padding = dip(10)
                                        backgroundColor = resources.getColor(R.color.colorPrimary)
                                        id =
                                            clubLogo
                                    }.lparams(width = matchParent, height = matchParent)
                                }.lparams(width = dip(100), height = dip(100))
                                textView {
                                    id =
                                        clubTitle
                                    textSize = sp(8).toFloat()
                                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                                    setTypeface(typeface, Typeface.BOLD)
                                }.lparams(width = matchParent) {
                                    marginStart = dip(10)
                                    topMargin = dip(60)
                                }
                            }.lparams(width = matchParent, height = matchParent){
                                margin = dip(10)
                            }
                        }.lparams(width = matchParent, height = dip(200)) {
                            marginEnd = dip(20)
                            marginStart = dip(20)
                            topMargin = dip(150)
                        }
                    }.lparams(width = matchParent, height = dip(300))
                    textView {
                        id = clubDesc
                    }.lparams(width = matchParent, height = matchParent) {
                        margin = dip(10)
                    }
                }
            }
        }
    }

}
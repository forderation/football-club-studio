package com.forderation.footballclubstudio

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.palette.graphics.Palette
import com.forderation.footballclubstudio.model.Club
import com.forderation.footballclubstudio.ui.ClubDetailUI
import com.squareup.picasso.Picasso
import org.jetbrains.anko.setContentView
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")

class ClubDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val clubDetailUI = ClubDetailUI().setContentView(this)
        val clubModel = intent.getParcelableExtra<Club>("club")
        val clubTitle:TextView = clubDetailUI.findViewById(ClubDetailUI.clubTitle)
        val clubDesc:TextView = clubDetailUI.findViewById(ClubDetailUI.clubDesc)
        val clubLogo:ImageView = clubDetailUI.findViewById(ClubDetailUI.clubLogo)
        clubTitle.text = clubModel?.clubName
        clubDesc.text = clubModel?.clubDesc
        Picasso.get().load(clubModel.clubLogo).placeholder(R.drawable.progress_animation).fit().into(clubLogo)
        val clubImg = BitmapFactory.decodeResource(applicationContext.resources,clubModel.clubLogo)
        Palette.generateAsync(clubImg){
            if (it != null) {
                clubTitle.setTextColor(it.getDarkVibrantColor(Color.BLACK))
            }
        }
    }
}

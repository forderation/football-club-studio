package com.forderation.footballclubstudio.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.activity.presenter.ClubDetailPresenter
import com.forderation.footballclubstudio.activity.view.ClubDetailView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.model.club.Club
import com.forderation.footballclubstudio.utils.BottomSheet
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_club_detail.*

class ClubDetailActivity : AppCompatActivity(), ClubDetailView {

    companion object{
        const val CLUB_ID = "CLUB_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_detail)
        val presenter = ClubDetailPresenter(this, Gson(), ApiClient())
        presenter.getDetailClub(intent.getStringExtra(CLUB_ID))
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun inflateDetailView(club: Club) {
        val picasso = Picasso.get()
        supportActionBar?.title = club.name
        picasso.load(club.getBadgeSmall()).fit().into(logo_club)
        picasso.load(club.getJerseySmall()).fit().into(team_jersey)
        picasso.load(club.getStadiumSmall()).into(img_stadium)
        tv_club_desc.text = club.strDescriptionEN
        tv_club_desc.setOnClickListener {
            BottomSheet(club.strDescriptionEN!!).show(supportFragmentManager,"ClubDetail")
        }
        tv_std_desc.text = club.strStadiumDescription
        tv_std_desc.setOnClickListener {
            BottomSheet(club.strStadiumDescription!!).show(supportFragmentManager,"ClubDetail")
        }
        tv_former_year.text = club.intFormedYear
        tv_web_club.text = club.strWebsite!!.replace("www.","")
        tv_fb_club.text = club.strFacebook?.replace("www.facebook.com/","")
        tv_ig_club.text = club.strInstagram?.replace("instagram.com/","")
        tv_tw_club.text = club.strTwitter?.replace("twitter.com/","")
        tv_std_name.text = club.strStadium
        tv_club_loc.text = club.strStadiumLocation
        tv_web_club.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(("https://").plus(club.strWebsite))
            startActivity(intent)
        }
        tv_ig_club.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(("https://").plus(club.strInstagram))
            startActivity(intent)
        }
        tv_fb_club.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(("https://").plus(club.strFacebook))
            startActivity(intent)
        }
        tv_tw_club.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(("https://").plus(club.strTwitter))
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                this.finish()
                true
            }
            else -> false
        }
    }
}

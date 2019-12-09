package com.forderation.footballclubstudio.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.R.drawable.*
import com.forderation.footballclubstudio.activity.presenter.ClubDetailPresenter
import com.forderation.footballclubstudio.activity.view.ClubDetailView
import com.forderation.footballclubstudio.adapter.BannerViewHolder
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.model.club.Club
import com.forderation.footballclubstudio.utils.BottomSheet
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.adapter.OnPageChangeListenerAdapter
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.bannerview.constants.IndicatorSlideMode
import com.zhpan.bannerview.utils.BannerUtils.dp2px
import kotlinx.android.synthetic.main.activity_club_detail.*
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.find

class ClubDetailActivity : AppCompatActivity(), ClubDetailView {

    companion object{
        const val CLUB_ID = "CLUB_ID"
        const val IS_FAV_CHANGE = "IS_FAV_CHANGE"
    }

    private var id: String? = null
    private lateinit var presenter:ClubDetailPresenter
    private lateinit var favMenu: MenuItem
    private var isFav = false
    private lateinit var mClub: Club

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_detail)
        presenter = ClubDetailPresenter(this, Gson(), ApiClient())
        presenter.context = this
        id = intent.getStringExtra(CLUB_ID)!!
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if(id!=null){
            isFav = presenter.checkFav(id!!)
            if(isFav){
                presenter.getDetailClubByDB(id!!)
            }else{
                presenter.getDetailClub(id)
            }
        }
    }

    override fun inflateDetailView(club: Club) {
        mClub = club
        val picasso = Picasso.get()
        supportActionBar?.title = club.name
        picasso.load(club.getBadgeSmall()).placeholder(progress_animation).error(image_failed).fit().into(logo_club)
        picasso.load(club.getJerseySmall()).placeholder(progress_animation).error(image_failed).fit().into(team_jersey)
        picasso.load(club.getStadiumSmall()).placeholder(progress_animation).error(image_failed).into(img_stadium)
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
        val listBanner = mutableListOf<String>()
        if(club.strTeamFanart1!=null){
            listBanner.add(club.strTeamFanart1)
        }
        if(club.strTeamFanart2!=null){
            listBanner.add(club.strTeamFanart2)
        }
        if(club.strTeamFanart3!=null){
            listBanner.add(club.strTeamFanart3)
        }
        if(club.strTeamFanart4!=null){
            listBanner.add(club.strTeamFanart4)
        }
        mViewPager = findViewById(R.id.banner_fan_art)
        mViewPager.setCanLoop(true)
            .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
            .setIndicatorMargin(0, 0, 0, dp2px(40f))
            .setIndicatorGravity(IndicatorGravity.CENTER)
            .setHolderCreator { BannerViewHolder() }
            .setOnPageChangeListener(
                object : OnPageChangeListenerAdapter() {
                    override fun onPageSelected(position: Int) {
                        listBanner[position]
                    }
                }
            )
            .create(listBanner.toList())
    }

    private lateinit var mViewPager: BannerViewPager<String, BannerViewHolder>

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_match, menu)
        favMenu = menu?.getItem(0)!!
        updateIcon()
        return true
    }

    private fun updateIcon() {
        if (isFav) {
            favMenu.icon = ContextCompat.getDrawable(this, ic_favorite_black_24dp)
        } else {
            favMenu.icon =
                ContextCompat.getDrawable(this, ic_favorite_border_black_24dp)
        }
    }

    override fun showMsg(msg: String) {
        sv_club_detail.longSnackbar(msg).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                this.finish()
                true
            }
            R.id.fav_match_item -> {
                if (!isFav) {
                    presenter.addToFav(mClub,id!!)
                    isFav = true
                    updateIcon()
                } else {
                    presenter.removeFav(id!!)
                    isFav = false
                    updateIcon()
                }
                setResult(Activity.RESULT_OK, Intent().putExtra(IS_FAV_CHANGE, true))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}

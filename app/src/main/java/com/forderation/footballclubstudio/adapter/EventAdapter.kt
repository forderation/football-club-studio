package com.forderation.footballclubstudio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.club.GetTeams
import com.forderation.footballclubstudio.model.event.Event
import com.forderation.footballclubstudio.utils.CoroutineContextProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find

class EventAdapter(
    private var eventList: List<Event>,
    private val context: CoroutineContextProvider = CoroutineContextProvider(),
    private val mListener: (Event, String, String) -> Unit
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_event, parent, false)
        return ViewHolder(view)
    }

    fun setEventList(newList: List<Event>) {
        eventList = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventList[position], mListener)
    }

    override fun getItemCount(): Int = eventList.size

    inner class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {

        private val titleEvent: TextView = mView.find(R.id.title_event)
        private val scoreHome: TextView = mView.find(R.id.score_home)
        private val scoreAway: TextView = mView.find(R.id.score_away)
        private val timeEvent: TextView = mView.find(R.id.time_event)
        private val dateEvent: TextView = mView.find(R.id.date_event)
        private val homeBadge: CircleImageView = mView.find(R.id.home_badge)
        private val awayBadge: CircleImageView = mView.find(R.id.away_badge)

        fun bind(
            mEvent: Event,
            mListener: (Event, String, String) -> Unit
        ) {
            titleEvent.text = mEvent.name
            scoreHome.text = mEvent.homeScore
            scoreAway.text = mEvent.awayScore
            timeEvent.text = mEvent.time
            dateEvent.text = mEvent.date
            var homeImgUrl = ""
            var awayImgUrl = ""
            val gson = Gson()
            val apiClient = ApiClient()
            GlobalScope.launch(context.main){
                val respHome = gson.fromJson(
                    apiClient.doRequestAsync(Endpoints.getDetailTeam(mEvent.idHome!!)).await(),
                    GetTeams::class.java
                )
                if (respHome.clubs!!.isEmpty()) {
                    Snackbar
                        .make(
                            mView,
                            "Error load data please check your connection",
                            Snackbar.LENGTH_SHORT
                        )
                        .show()
                } else {
                    homeImgUrl = respHome.clubs[0].getBadgeSmall()
                    Picasso.get()
                        .load(homeImgUrl)
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.image_failed)
                        .into(homeBadge)
                }
                val respAway = gson.fromJson(
                    apiClient.doRequestAsync(Endpoints.getDetailTeam(mEvent.idAway!!)).await(),
                    GetTeams::class.java
                )
                if (respAway.clubs!!.isEmpty()) {
                    Snackbar
                        .make(
                            mView,
                            "Error load data please check your connection",
                            Snackbar.LENGTH_SHORT
                        )
                        .show()
                } else {
                    awayImgUrl = respAway.clubs[0].getBadgeSmall()
                    Picasso.get()
                        .load(awayImgUrl)
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.image_failed)
                        .into(awayBadge)
                }
                mView.setOnClickListener { mListener(mEvent, homeImgUrl, awayImgUrl) }
            }
        }
    }
}

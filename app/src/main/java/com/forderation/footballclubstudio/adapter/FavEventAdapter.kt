package com.forderation.footballclubstudio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.db.FavEvent
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.find

class FavEventAdapter(
    private var eventList: List<FavEvent>,
    private val mListener: (FavEvent, String, String) -> Unit
) : RecyclerView.Adapter<FavEventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_event, parent, false)
        return ViewHolder(view)
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
            mEvent: FavEvent,
            mListener: (FavEvent, String, String) -> Unit
        ) {
            titleEvent.text = mEvent.name
            scoreHome.text = mEvent.homeScore
            scoreAway.text = mEvent.awayScore
            timeEvent.text = mEvent.time
            dateEvent.text = mEvent.date
            mView.setOnClickListener { mListener(mEvent, mEvent.homeBadge!!, mEvent.awayBadge!!) }
            if(mEvent.homeBadge!!.isNotEmpty() && mEvent.awayBadge!!.isNotEmpty()){
                Picasso.get()
                    .load(mEvent.homeBadge)
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.image_failed)
                    .fit()
                    .centerInside()
                    .into(homeBadge)
                Picasso.get()
                    .load(mEvent.awayBadge)
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.image_failed)
                    .fit()
                    .centerInside()
                    .into(awayBadge)
            }
        }
    }
}

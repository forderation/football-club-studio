package com.forderation.footballclubstudio.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.model.club.Club
import com.forderation.footballclubstudio.model.event.Event
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.find

class EventAdapter(
    private val eventList: List<Event>,
    private val clubList: List<Club>,
    private val mListener: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventList[position],clubList,mListener)
    }

    override fun getItemCount(): Int = eventList.size

    inner class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {

        private val titleEvent:TextView = mView.find(R.id.title_event)
        private val scoreHome:TextView = mView.find(R.id.score_home)
        private val scoreAway:TextView = mView.find(R.id.score_away)
        private val timeEvent:TextView = mView.find(R.id.time_event)
        private val dateEvent:TextView = mView.find(R.id.date_event)
        private val homeBadge:CircleImageView = mView.find(R.id.home_badge)
        private val awayBadge:CircleImageView = mView.find(R.id.away_badge)

        fun bind(
            mEvent: Event,
            clubList: List<Club>,
            mListener: (Event) -> Unit
        ) {
            titleEvent.text = mEvent.name
            scoreHome.text = mEvent.homeScore
            scoreAway.text = mEvent.awayScore
            timeEvent.text = mEvent.time
            dateEvent.text = mEvent.date
            clubList.forEach {
                if(it.id.equals(mEvent.idHome)){
                    Picasso.get().load(it.badge).fit().centerInside().into(homeBadge)
                }else if(it.id.equals(mEvent.idAway)){
                    Picasso.get().load(it.badge).fit().centerInside().into(awayBadge)
                }
            }
            mView.setOnClickListener { mListener(mEvent) }
        }
    }
}

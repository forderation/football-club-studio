package com.forderation.footballclubstudio.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.model.club.Club
import org.jetbrains.anko.layoutInflater
import com.forderation.footballclubstudio.R.id.*
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class ClubAdapter(private val clickItemListener: (Club) -> Unit) :
    RecyclerView.Adapter<ClubAdapter.VHolder>() {

    var clubList: MutableList<Club> = arrayListOf()
        set(value) {
            clubList.clear()
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(parent.context.layoutInflater.inflate(R.layout.item_club, parent, false))
    }

    override fun getItemCount(): Int = clubList.size

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(clubList[position], clickItemListener)
    }

    class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvClubName = itemView.find<TextView>(tv_club)
        val tvClubImg = itemView.find<ImageView>(logo_club)
        fun bind(club: Club, listener: (Club) -> Unit) {
            Picasso.get().load(club.getBadgeSmall()).fit().centerInside().into(tvClubImg)
            tvClubName.text = club.name
            itemView.setOnClickListener { listener(club) }
        }
    }
}
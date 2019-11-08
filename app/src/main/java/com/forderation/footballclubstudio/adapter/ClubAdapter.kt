package com.forderation.footballclubstudio.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forderation.footballclubstudio.ui.ClubItemUI
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.model.Club
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext

@Suppress("DEPRECATION")
class ClubAdapter(private var clubList: List<Club>, private val clickItemListener: (Club) -> Unit): RecyclerView.Adapter<ClubAdapter.VHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(
            ClubItemUI().createView(
                AnkoContext.create(parent.context, parent, false)
            )
        )
    }

    override fun getItemCount(): Int = clubList.size

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(clubList[position],clickItemListener)
    }

    class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(ClubItemUI.clubImg)
        private val nameClub: TextView = itemView.findViewById(ClubItemUI.clubName)
        fun bind(itemClub: Club, clickItemListener: (Club) -> Unit){
            Picasso.get().load(itemClub.clubLogo).placeholder(R.drawable.progress_animation).fit().into(image)
            nameClub.text = itemClub.clubName
            itemView.setOnClickListener {
                clickItemListener(itemClub)
            }
        }
    }
}
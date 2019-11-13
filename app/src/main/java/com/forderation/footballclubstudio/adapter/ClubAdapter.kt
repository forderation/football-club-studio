package com.forderation.footballclubstudio.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.forderation.footballclubstudio.model.league.League

@Suppress("DEPRECATION")
class ClubAdapter(private var clubList: List<League>, private val clickItemListener: (League) -> Unit): RecyclerView.Adapter<ClubAdapter.VHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        TODO()
    }

    override fun getItemCount(): Int = clubList.size

    override fun onBindViewHolder(holder: VHolder, position: Int) {

    }

    class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
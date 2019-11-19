package com.forderation.footballclubstudio.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.adapter.LeagueAdapter.VHolder
import com.forderation.footballclubstudio.R.id.*
import com.forderation.footballclubstudio.model.league.League
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater

class LeagueAdapter(
    private var leagueList:MutableList<League>,
    private var ctx:Context,
    private var listener:(League) -> Unit): RecyclerView.Adapter<VHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(ctx.layoutInflater.inflate(R.layout.item_league,parent,false))
    }

    fun clearAdapter(){
        leagueList.clear()
        notifyDataSetChanged()
    }

    fun addItem(league: League){
        leagueList.add(league)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = leagueList.size

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(leagueList[position],listener)
    }

    class VHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        private val imgLeague:ImageView = itemView.find(img_league)
        private val titleLeague:TextView = itemView.find(title_league)
        private val descLeague:TextView = itemView.find(desc_league)
        private val backLeague:ImageView = itemView.find(background_league)
        fun bind(league:League, leagueListener: (League) -> Unit){
            Picasso.get().load(league.badge).fit().centerInside().into(imgLeague)
            Picasso.get().load(league.smallBackground())
                .transform(BlurTransformation(itemView.context, 25, 1))
                .fit().centerCrop().into(backLeague)
            titleLeague.text = league.name
            descLeague.text = league.description
            itemView.setOnClickListener { leagueListener(league) }
        }
    }
}
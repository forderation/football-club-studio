package com.forderation.footballclubstudio.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forderation.footballclubstudio.R
import com.forderation.footballclubstudio.R.id.*
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.classement.TeamClassement
import com.forderation.footballclubstudio.model.club.GetClub
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater

class ClassementAdapter(
    private var teamClassementList: List<TeamClassement>,
    private val mListener: (String) -> Unit
):RecyclerView.Adapter<ClassementAdapter.VHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(parent.context.layoutInflater.inflate(R.layout.item_classement,parent,false))
    }

    override fun getItemCount(): Int {
        return teamClassementList.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(teamClassementList[position], mListener)
    }

    class VHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val imgLogo = itemView.find<ImageView>(logo_club)
        private val tvPlayed = itemView.find<TextView>(tv_played)
        private val tvGoalsFor = itemView.find<TextView>(tv_goals_for)
        private val tvGoalsAgainst = itemView.find<TextView>(tv_goal_against)
        private val tvGoalsDiffer = itemView.find<TextView>(tv_goals_differ)
        private val tvWin = itemView.find<TextView>(tv_win)
        private val tvLoss = itemView.find<TextView>(tv_loss)
        private val tvDraw = itemView.find<TextView>(tv_draw)
        private val tvTotal = itemView.find<TextView>(tv_total)
        private val tvNameClub = itemView.find<TextView>(tv_club_name)

        fun bind(teamClassement: TeamClassement, listener: (String) -> Unit){
            GlobalScope.launch(Dispatchers.Main){
                val resp = Gson().fromJson(
                    ApiClient().doRequestAsync(Endpoints.getDetailTeam(teamClassement.teamid.toString())).await(),
                    GetClub::class.java
                )
                Picasso.get().load(resp.clubs?.get(0)?.getBadgeSmall()).fit().into(imgLogo)
            }
            itemView.setOnClickListener { listener(teamClassement.teamid.toString()) }
            tvNameClub.text = teamClassement.name
            tvPlayed.text = teamClassement.played.toString()
            tvGoalsFor.text = teamClassement.goalsfor.toString()
            tvGoalsAgainst.text = teamClassement.goalsagainst.toString()
            tvGoalsDiffer.text = teamClassement.goalsdifference.toString()
            tvWin.text = teamClassement.win.toString()
            tvLoss.text = teamClassement.loss.toString()
            tvDraw.text = teamClassement.draw.toString()
            tvTotal.text = teamClassement.total.toString()
        }
    }
}
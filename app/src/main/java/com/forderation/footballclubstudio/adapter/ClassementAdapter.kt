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
import com.forderation.footballclubstudio.model.classement.Team
import com.forderation.footballclubstudio.model.club.GetTeams
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater

class ClassementAdapter(
    private var teamList: List<Team>,
    private val mListener: (String) -> Unit
):RecyclerView.Adapter<ClassementAdapter.VHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(parent.context.layoutInflater.inflate(R.layout.item_classement,parent,false))
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(teamList[position], mListener)
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

        fun bind(team: Team, listener: (String) -> Unit){
            GlobalScope.launch(Dispatchers.Main){
                val resp = Gson().fromJson(
                    ApiClient().doRequestAsync(Endpoints.getDetailTeam(team.teamid.toString())).await(),
                    GetTeams::class.java
                )
                Picasso.get().load(resp.clubs?.get(0)?.getBadgeSmall()).fit().into(imgLogo)
            }
            tvNameClub.text = team.name
            tvPlayed.text = team.played.toString()
            tvGoalsFor.text = team.goalsfor.toString()
            tvGoalsAgainst.text = team.goalsagainst.toString()
            tvGoalsDiffer.text = team.goalsdifference.toString()
            tvWin.text = team.win.toString()
            tvLoss.text = team.loss.toString()
            tvDraw.text = team.draw.toString()
            tvTotal.text = team.total.toString()
        }
    }
}
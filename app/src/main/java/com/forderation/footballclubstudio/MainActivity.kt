package com.forderation.footballclubstudio

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity() {

    var items:MutableList<Club> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareData()
        ClubListUI(ClubAdapter(items){

        }).setContentView(this)
    }

    @SuppressLint("Recycle")
    fun prepareData(){
        val image = resources.obtainTypedArray(R.array.club_logo)
        val name = resources.getStringArray(R.array.club_title)
        val desc = resources.getStringArray(R.array.club_desc)
        items.clear()
        name.forEachIndexed{i,e ->
            items.add(Club(e,desc[i],image.getResourceId(i,0)))
        }
        image.recycle()
    }
}

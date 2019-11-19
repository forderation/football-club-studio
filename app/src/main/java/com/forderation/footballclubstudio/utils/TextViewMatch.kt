package com.forderation.footballclubstudio.utils

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

class TextViewMatch @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    fun setText(text: String?, act: Activity){
        val ctx = act as FragmentActivity
        var reText = text ?:""
        if(reText.isEmpty()){
            reText = "-"
        }
        setText(reText)
        setOnClickListener {
            BottomSheet(reText).show(ctx.supportFragmentManager, "BottomSheet")
        }
    }
}
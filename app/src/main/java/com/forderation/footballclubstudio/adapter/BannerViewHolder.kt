package com.forderation.footballclubstudio.adapter

import android.view.View
import android.widget.ImageView
import com.forderation.footballclubstudio.R
import com.squareup.picasso.Picasso
import com.zhpan.bannerview.holder.ViewHolder
import org.jetbrains.anko.find

class BannerViewHolder : ViewHolder<String> {

    override fun getLayoutId(): Int {
        return R.layout.item_banner
    }


    override fun onBind(itemView: View?, data: String, position: Int, size: Int) {
        val imgView = itemView?.find<ImageView>(R.id.banner_image)
        Picasso.get().load(data).fit().into(imgView)
    }

}
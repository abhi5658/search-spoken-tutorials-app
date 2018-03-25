package com.example.abhishek.firstkotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_item.view.*

/**
 * Created by abhishek on 19/3/18.
 */

class VideoAdapter(val mContext: Context, val videoList: ArrayList<VideoItem>) : RecyclerView.Adapter<VideoAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {

        val layoutInflator = LayoutInflater.from(parent?.context)
        val videoItem = layoutInflator.inflate(R.layout.video_item, parent, false)
        return MyViewHolder(videoItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        var singleVideoItem = videoList.get(position)
        holder?.itemView?.video_title?.text = singleVideoItem.videoId

        Picasso.with(mContext)
                .load("https://img.youtube.com/vi/${singleVideoItem.videoId}/hqdefault.jpg")
                .resize(320, 180)
                .centerCrop()
                .into(holder?.itemView?.video_thumbnail)

    }

    override fun getItemCount(): Int {
        return videoList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}

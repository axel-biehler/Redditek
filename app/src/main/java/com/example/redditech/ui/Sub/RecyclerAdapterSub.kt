package com.example.redditech.ui.Sub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.redditech.R

class RecyclerAdapterSub(private var users: List<String>, private var titles: List<String>, private var details: List<String>, private var images: List<Int>, private var imagesPost: List<Int>):
RecyclerView.Adapter<RecyclerAdapterSub.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemUser: TextView = itemView.findViewById(R.id.user_name)
        val itemTitle: TextView = itemView.findViewById(R.id.post_title)
        val itemDetail: TextView = itemView.findViewById(R.id.post_description)
        val itemPicture: ImageView = itemView.findViewById(R.id.iv_image)
        val itemImage: ImageView = itemView.findViewById(R.id.post_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemUser.text = users[position]
        holder.itemTitle.text = titles[position]
        holder.itemDetail.text = details[position]
        holder.itemPicture.setImageResource(images[position])
        holder.itemImage.setImageResource(imagesPost[position])
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}
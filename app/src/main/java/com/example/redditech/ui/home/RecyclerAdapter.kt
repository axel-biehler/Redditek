package com.example.redditech.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.redditech.R

class RecyclerAdapter(private var titles: List<String>, private var details: List<String>, private var images: List<Int>):
RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
        val itemDetail: TextView = itemView.findViewById(R.id.tv_description)
        val itemPicture: ImageView = itemView.findViewById(R.id.iv_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDetail.text = details[position]
        holder.itemPicture.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}
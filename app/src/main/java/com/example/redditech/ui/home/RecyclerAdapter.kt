package com.example.redditech.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.redditech.R
import com.example.redditech.api.Post
import com.squareup.picasso.Picasso

class RecyclerAdapter(private var posts: List<Post>):
RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemSub: TextView = itemView.findViewById(R.id.sub_name)
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
        holder.itemSub.text = posts[position].data.subreddit
        holder.itemUser.text = posts[position].data.author
        holder.itemTitle.text = posts[position].data.title
        holder.itemDetail.text = posts[position].data.selftext
        Picasso.get().load(posts[position].data.url).into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return posts.size
    }


}
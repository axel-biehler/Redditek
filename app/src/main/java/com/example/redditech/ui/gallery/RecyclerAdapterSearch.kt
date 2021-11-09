package com.example.redditech.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.redditech.R
import com.example.redditech.ui.Sub.Sub

class RecyclerAdapterSearch(private var subs: List<String>, private var images: List<Int>):
RecyclerView.Adapter<RecyclerAdapterSearch.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemSub: TextView = itemView.findViewById(R.id.sub_name_search)
        val itemPicture: ImageView = itemView.findViewById(R.id.iv_image_search)

        init {
            itemView.setOnClickListener { v : View ->
                val position : Int = adapterPosition
                //Toast.makeText(itemView.context, "You clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()
                val activity = v.context as AppCompatActivity
                val redirectFragment = newInstance(position)
                activity.supportFragmentManager.beginTransaction().replace(R.id.search_fragment, redirectFragment).commit()
            }
        }
    }
    fun newInstance(position: Int): Sub {
        val f = Sub()
        val args = Bundle()
        args.putString("sub", subs[position])
        f.arguments = args
        return f
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_search_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemSub.text = subs[position]
        holder.itemPicture.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return subs.size
    }
}
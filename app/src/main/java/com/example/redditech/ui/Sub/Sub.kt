package com.example.redditech.ui.Sub

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redditech.R
import com.example.redditech.ui.home.RecyclerAdapter
import kotlinx.android.synthetic.main.sub_fragment.*

class Sub : Fragment() {
    private var usersList = mutableListOf<String>()
    private var titlesList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesSubsList = mutableListOf<Int>()
    private var imagesList = mutableListOf<Int>()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    companion object {
        fun newInstance() = Sub()
    }

    private lateinit var viewModel: SubViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        postToList()

        return inflater.inflate(R.layout.sub_fragment, container, false)
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        rv_recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = RecyclerAdapterSub(usersList, titlesList, descList, imagesList, imagesSubsList)
            val args = arguments
            val index = args?.getString("sub", "")
            sub_name.text = index?.drop(2).toString()
        }
    }

    private fun addToList(user: String, title: String, description: String, image: Int, subImage: Int) {
        usersList.add(user)
        titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
        imagesSubsList.add(subImage)
    }

    private fun postToList() {
        for (i in 1..25) {
            addToList( "User $i", "Title Title Title Title Title Title Title Title Title Title Title Title Title Title Title $i", "Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description $i", R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round)
        }
    }
}
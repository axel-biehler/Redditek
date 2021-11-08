package com.example.redditech.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redditech.R
import com.example.redditech.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private var subsList = mutableListOf<String>()
    private var usersList = mutableListOf<String>()
    private var titlesList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesSubsList = mutableListOf<Int>()
    private var imagesList = mutableListOf<Int>()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        postToList()

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        rv_recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = RecyclerAdapter(subsList, usersList, titlesList, descList, imagesList, imagesSubsList)
        }
    }

    private fun addToList(sub: String, user: String, title: String, description: String, image: Int, subImage: Int) {
        subsList.add(sub)
        usersList.add(user)
        titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
        imagesSubsList.add(subImage)
    }

    private fun postToList() {
        for (i in 1..25) {
            addToList("Sub $i", "User $i", "Title Title Title Title Title Title Title Title Title Title Title Title Title Title Title $i", "Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description $i", R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
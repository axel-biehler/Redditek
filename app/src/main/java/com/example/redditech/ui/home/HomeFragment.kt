package com.example.redditech.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginEnd
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redditech.MainActivity
import com.example.redditech.NavigationDrawerActivity
import com.example.redditech.R
import com.example.redditech.api.*
import com.example.redditech.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var postList = mutableListOf<Post>()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var recycler: RecyclerView
    private var _binding: FragmentHomeBinding? = null
    private var _after: String = "null"
    private var waiting = false
    private var type: String = "best"


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

        val recyclerView: RecyclerView = root.findViewById(R.id.rv_recyclerView)

        recycler = recyclerView
        setRecyclerViewScrollListener()

        return root
    }

    override fun onStart() {
        super.onStart()

        getBestPublication(4, _after, )
    }

    private fun addToList(post: Post) {
        postList.add(post)
    }

    private fun postToList(posts: ResponsePost) {
        for (i in posts.data.children) {
            addToList(i)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getBestPublication(limit: Number, after: String) {
        val apiClient = ApiClient()
        val activity: NavigationDrawerActivity = getActivity() as NavigationDrawerActivity


        apiClient.getApiService(activity).getBestPublicationList(
            Constants.BASE_URL +
                    "${type}?limit=$limit&after=$after").enqueue(object :
            Callback<ResponsePost> {
            override fun onResponse(call: Call<ResponsePost>, response: Response<ResponsePost>) {
                val postPage: ResponsePost = response.body()!!
                postToList(postPage)
                if (_after == "null") {
                    rv_recyclerView.apply {
                        // set a LinearLayoutManager to handle Android
                        // RecyclerView behavior
                        layoutManager = LinearLayoutManager(activity)
                        // set the custom adapter to the RecyclerView

                        adapter = RecyclerAdapter(postList)
                    }
                }
                _after = postPage.data.after
                waiting = false
                recycler.adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ResponsePost>, t: Throwable) {
                Log.d("REQUEST PUBLICATION", t.message.toString())
            }

        })
    }

    fun getSubredditInfo(name:String) {
        val apiClient = ApiClient()
        val activity: NavigationDrawerActivity = getActivity() as NavigationDrawerActivity

        apiClient.getApiService(activity).getSubredditInfo("${Constants.BASE_URL}" +
                "r/$name/about").enqueue(object : Callback<Subreddit> {
            override fun onResponse(call: Call<Subreddit>, response: Response<Subreddit>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<Subreddit>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0 && !waiting) {
                    waiting = true
                    getBestPublication(3, _after)
                    Log.d("END", "gere")
                }
            }
        }
        recycler.addOnScrollListener(scrollListener)
    }

    fun setFilter(filter: String) {
        _after = "null"
        type = filter
        postList.clear()
        getBestPublication(4, "null")
    }
}
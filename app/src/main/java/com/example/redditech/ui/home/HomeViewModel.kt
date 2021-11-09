package com.example.redditech.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redditech.api.Post

class HomeViewModel() : ViewModel() {
    private lateinit var posts: MutableList<MutableLiveData<Post>>

    fun setPost(data: MutableList<Post>) {
        val tmp: MutableLiveData<Post> = MutableLiveData<Post>()

        for (i in data) {
            tmp.value = i
            posts.add(tmp)
        }
    }

    fun getPosts(): MutableList<MutableLiveData<Post>> {
        return posts
    }

}
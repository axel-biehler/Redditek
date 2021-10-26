package com.example.redditech

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import okhttp3.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    private val _authUrl = "https://www.reddit.com/api/v1/authorize?client_id=%s&response_type=token&" +
            "state=%s&redirect_uri=%s&scope=identity"

    private val _clientId = "pCYkCQhXhtAZ8AHGHGgzuA"

    private val _redirectUri = "http://www.reditech.local/my_redirect"

    private val _state = "continue"

    fun startSignIn(view: View?) {
        val url: String = java.lang.String.format(
            _authUrl,
            _clientId,
            _state,
            _redirectUri
        )
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        if (intent != null && intent.action == Intent.ACTION_VIEW) {
            val uri = intent.data
            var patchedUri = Uri.parse(uri.toString().replace('#', '?'))
            if (uri!!.getQueryParameter("error") != null) {
                val error = patchedUri.getQueryParameter("error")
                Log.e(TAG, "An error has occurred : $error")
            } else {
                val state = patchedUri.getQueryParameter("state")
                if (state == _state) {
                    val access_token = patchedUri.getQueryParameter("access_token")
                    Log.d("ACCESS_TOKEN", "$access_token")
                    val intent = Intent(this, NavigationDrawerActivity::class.java).apply {
                        intent.putExtra("token", access_token)
                    }
                    startActivity(intent);
                }
            }
        }
    }


}
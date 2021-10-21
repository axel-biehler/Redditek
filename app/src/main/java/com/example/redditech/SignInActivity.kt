package com.example.redditech

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.net.wifi.rtt.CivicLocationKeys
import android.view.View
import android.net.wifi.rtt.CivicLocationKeys.STATE
import android.util.Log


class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    private val _authUrl = "https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
            "&response_type=code&state=%s&redirect_uri=%s&" +
            "duration=permanent&scope=identity"

    private val _clientId = "pCYkCQhXhtAZ8AHGHGgzuA"

    private val _redirectUri = "http://www.reddit.local/my_redirect"

    private val _state = "connection"

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
            if (uri!!.getQueryParameter("error") != null) {
                val error = uri.getQueryParameter("error")
                Log.e(TAG, "An error has occurred : $error")
            } else {
                val state = uri.getQueryParameter("state")
                if (state == _state) {
                    val code = uri.getQueryParameter("code")
                    println("mon code : $code")
                }
            }
        }
    }
}
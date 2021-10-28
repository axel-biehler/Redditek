package com.example.redditech

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.redditech.api.*

import okhttp3.*
import okhttp3.OkHttpClient
import org.json.JSONException

import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.CountDownLatch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    private val _authUrl = "https://www.reddit.com/api/v1/authorize.compact?client_id=%s&response_type=code&" +
            "state=%s&redirect_uri=%s&duration=temporary&scope=identity"

    private val _clientId = "FjegaTqKa4vN2OoCi6gtxw"

    private val _redirectUri = "http://www.reditech.local/my_redirect"

    private val _state = "continue"

    private val _accessUrl = "https://www.reddit.com/api/v1/access_token"

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
                    val code = patchedUri.getQueryParameter("code")
                    if (code != null) {
                        Log.d(TAG, code)
                    }
                    val accessToken = getAccessToken(code)
                    //val intent = Intent(this, NavigationDrawerActivity::class.java).apply {
                    //    intent.putExtra("token", code)
                    //}
                    //startActivity(intent);
                }
            }
        }
    }

    private fun getAccessToken(code: String?) {
        val countDownLatch = CountDownLatch(1)
        val client = OkHttpClient()
        val authString = "$_clientId:"
        val encodedAuthString: String = Base64.encodeToString(
            authString.toByteArray(),
            Base64.NO_WRAP
        )
        val request = Request.Builder()
            .addHeader("User-Agent", "Sample App")
            .addHeader("Authorization", "Basic $encodedAuthString")
            .url(_accessUrl)
            .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                "grant_type=authorization_code&code=$code" +
                        "&redirect_uri=$_redirectUri"))
            .build();

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException) {
                Log.e(TAG, "ERROR: $e")
                countDownLatch.countDown()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response) {
                val json = response.body()!!.string()
                Log.d(TAG, json)
                var data: JSONObject? = null
                try {
                    data = JSONObject(json)
                    val accessToken = data.optString("access_token")
                    val refreshToken = data.optString("refresh_token")
                    Log.d(TAG, "Access Token = $accessToken")
                    Log.d(TAG, "Refresh Token = $refreshToken")
                    countDownLatch.countDown()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    countDownLatch.countDown()
                }
            }
        })
        countDownLatch.await()
    }
}
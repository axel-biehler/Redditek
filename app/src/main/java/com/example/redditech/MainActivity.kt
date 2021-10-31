package com.example.redditech

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.redditech.api.SessionManager
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.CountDownLatch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    private val AUTH_URL = "https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
            "&response_type=code&state=%s&redirect_uri=%s&" +
            "duration=permanent&scope=identity mysubreddits read"

    private val CLIENT_ID = "xwZXxnD7i1F9CCQdASN-zw"

    private val REDIRECT_URI = "http://www.reditech.local/my_redirect"

    private val STATE = "MY_RANDOM_STRING_1"

    private val ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token"

    private var _token = ""

    fun startSignIn(view: View?) {
        val url = String.format(AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI)
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
                if (state == STATE) {
                    val code = uri.getQueryParameter("code")
                    getAccessToken(code)
                    val sessionManager = SessionManager(this)
                    sessionManager.saveAuthToken(_token)
                    val intent = Intent(this, NavigationDrawerActivity::class.java).apply {
                        intent.putExtra("token", _token)
                    }
                    startActivity(intent);
                }
            }
        }
    }

    private fun getAccessToken(code: String?) {
        val countDownLatch = CountDownLatch(1)
        val client = OkHttpClient()
        val authString = "$CLIENT_ID:"
        val encodedAuthString = Base64.encodeToString(
            authString.toByteArray(),
            Base64.NO_WRAP
        )
        val request = Request.Builder()
            .addHeader("User-Agent", "Sample App")
            .addHeader("Authorization", "Basic $encodedAuthString")
            .url(ACCESS_TOKEN_URL)
            .post(
                ("grant_type=authorization_code&code=" + code.toString() +
                        "&redirect_uri=" + REDIRECT_URI
                        ).toRequestBody("application/x-www-form-urlencoded".toMediaTypeOrNull())
            )
            .build()
        val token: String
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "ERROR: $e")
                countDownLatch.countDown()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val json = response.body!!.string()
                var data: JSONObject? = null
                try {
                    data = JSONObject(json)
                    setAccessToken(data.optString("access_token"))
                    Log.d(TAG, "Access Token = $_token")
                    countDownLatch.countDown()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    countDownLatch.countDown()
                }
            }
        })
        countDownLatch.await()
    }

    fun setAccessToken(token: String) {
        _token = token
    }

}
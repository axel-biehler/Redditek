package com.example.redditech

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.redditech.api.ApiClient
import com.example.redditech.api.User
import com.example.redditech.databinding.ActivityNavigationDrawerBinding
import com.google.android.material.navigation.NavigationView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.example.redditech.api.Constants
import com.example.redditech.api.ResponsePost


class NavigationDrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavigationDrawerBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavigationDrawer.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_navigation_drawer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        setUserInfo()
        //getBestPublication(3, "null")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setUserInfo() {
        val apiClient = ApiClient()
        apiClient.getApiService(this).userInfo()
            .enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    // Error fetching posts
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val textView = findViewById<TextView>(R.id.nav_header)
                    val user = response.body()

                    textView.text = user?.name
                    //getImage(user?.avatar)
                }
            })
    }

    private fun getImage(url: String?) {
        val apiClient = ApiClient()
        apiClient.getApiService(this).getAvatar(url).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val bytes: ByteArray = response.body()!!.bytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                val imageView = findViewById<ImageView>(R.id.imageViewAvatar)

                imageView.setImageBitmap(bitmap);
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //TODO("Not yet implemented")
            }

        })
    }

    private fun getBestPublication(limit: Number, after: String) {
        val apiClient = ApiClient()

        apiClient.getApiService(this).getBestPublicationList(
            Constants.BASE_URL +
                    "${Constants.PUBLICATION_BEST}?limit=$limit&after=$after").enqueue(object : Callback<ResponsePost> {
            override fun onResponse(call: Call<ResponsePost>, response: Response<ResponsePost>) {
                Log.d("REQUEST", "On response success")
            }

            override fun onFailure(call: Call<ResponsePost>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}
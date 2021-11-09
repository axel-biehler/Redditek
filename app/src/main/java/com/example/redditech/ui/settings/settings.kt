package com.example.redditech.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.redditech.NavigationDrawerActivity
import com.example.redditech.R
import com.example.redditech.api.ApiClient
import com.example.redditech.api.GetterSettings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.settings_fragment.*

class settings : Fragment() {

    companion object {
        fun newInstance() = settings()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getUserSettings()



        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun getUserSettings() {
        val apiClient = ApiClient()
        val activity: NavigationDrawerActivity = getActivity() as NavigationDrawerActivity

        apiClient.getApiService(activity).userSettings().enqueue(object :
        Callback<GetterSettings> {
            override fun onResponse(
                call: Call<GetterSettings>,
                response: Response<GetterSettings>
            ) {
                var enable_followers = response.body()?.enable_followers
                var feed_recommendations_enabled = response.body()?.feed_recommendations_enabled
                var activity_relevant_ads = response.body()?.activity_relevant_ads
                var email_unsubscribe_all = response.body()?.email_unsubscribe_all
                var hide_from_robots = response.body()?.hide_from_robots
                var show_location_based_recommendations = response.body()?.show_location_based_recommendations

                if (enable_followers != null) {
                    switch1.isChecked = enable_followers
                }
                if (email_unsubscribe_all != null) {
                    switch2.isChecked = email_unsubscribe_all
                }
                if (hide_from_robots != null) {
                    switch3.isChecked = hide_from_robots
                }
                if (feed_recommendations_enabled != null) {
                    switch4.isChecked = feed_recommendations_enabled
                }
                if (activity_relevant_ads != null) {
                    switch5.isChecked = activity_relevant_ads
                }
                if (show_location_based_recommendations != null) {
                    switch6.isChecked = show_location_based_recommendations
                }
            }

            override fun onFailure(call: Call<GetterSettings>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}
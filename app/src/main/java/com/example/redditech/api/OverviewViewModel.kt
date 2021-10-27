package com.example.redditech.api

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status

    fun getUser(token: String?, context: Context) {
        viewModelScope.launch {
            try {
                val user : String = getApiService(context, token).getUser()
                Log.d("user", user)
                _status.value = "Success: User information"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
            _status.value?.let { Log.d("STATUS", it) }
        }
    }
}
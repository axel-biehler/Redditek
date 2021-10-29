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
    private val _user = MutableLiveData<User>()

    // The external immutable LiveData for the request status
    val user: LiveData<User> = _user

    fun getUser(token: String?, context: Context) : LiveData<User> {
        viewModelScope.launch {
            try {
                val user : User = getApiService(context, token).getUser()
                _user.value = user
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
            _status.value?.let { Log.d("STATUS", it) }
            _user.postValue(user.value)
        }
        return user
    }
}
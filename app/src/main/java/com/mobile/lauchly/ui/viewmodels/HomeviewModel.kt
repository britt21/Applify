package com.mobile.lauchly.ui.viewmodels

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.mobile.lauchly.data.AppInfo
import com.mobile.lauchly.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeviewModel(
    private val repository: UserRepository
) : ViewModel() {


    private val _allApps = MutableLiveData<List<AppInfo>>()
    val allApps: LiveData<List<AppInfo>> = _allApps

    fun loadApps(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val apps = repository.getAllApps(context)
            _allApps.postValue(apps)

            println("Apps loaded: ${apps}")
        }
    }


}
package com.mobile.lauchly.ui.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.lauchly.repository.UserRepository

class HomeViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeviewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeviewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }



}

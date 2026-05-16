package com.utama.tugas5

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HujanViewModelFactory(private val repository: HujanRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HujanViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HujanViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
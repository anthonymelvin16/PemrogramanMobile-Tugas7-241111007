package com.utama.tugas5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HujanViewModel(private val repository: HujanRepository) : ViewModel() {

    private val _dataList = MutableLiveData<List<DataHujanApi>>()
    val dataList: LiveData<List<DataHujanApi>> = _dataList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init { loadData() }

    fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _dataList.value = repository.getAllData()
            } catch (e: Exception) {
                _errorMessage.value = "Gagal memuat data: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addData(request: HujanRequest) {
        viewModelScope.launch {
            try {
                repository.addData(request)
                loadData()
            } catch (e: Exception) {
                _errorMessage.value = "Gagal menambah data: ${e.message}"
            }
        }
    }

    fun deleteData(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteData(id)
                loadData()
            } catch (e: Exception) {
                _errorMessage.value = "Gagal menghapus data: ${e.message}"
            }
        }
    }
}
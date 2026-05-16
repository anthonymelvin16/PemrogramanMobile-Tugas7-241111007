package com.utama.tugas5

class HujanRepository {

    private val api = RetrofitClient.apiService

    suspend fun getAllData(): List<DataHujanApi> {
        val response = api.getAllData()
        if (response.isSuccessful) {
            val body = response.body()
            if (body?.success == true) {
                return body.data ?: emptyList()
            }
            throw Exception(body?.message ?: "Gagal mengambil data")
        }
        throw Exception("HTTP Error: ${response.code()}")
    }

    suspend fun addData(request: HujanRequest): Boolean {
        val response = api.addData(request)
        if (response.isSuccessful) {
            return response.body()?.success == true
        }
        throw Exception("Gagal menambahkan data")
    }

    suspend fun deleteData(id: Int): Boolean {
        val response = api.deleteData(id)
        if (response.isSuccessful) {
            return response.body()?.success == true
        }
        throw Exception("Gagal menghapus data")
    }
}
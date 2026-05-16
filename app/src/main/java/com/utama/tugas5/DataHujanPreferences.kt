package com.utama.tugas5

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataHujanPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("data_hujan_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val KEY = "list_data_hujan"

    // READ - ambil semua data
    fun getAll(): MutableList<DataHujan> {
        val json = prefs.getString(KEY, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<DataHujan>>() {}.type
        return gson.fromJson(json, type)
    }

    // CREATE - tambah data baru
    fun add(data: DataHujan) {
        val list = getAll()
        val newId = if (list.isEmpty()) 1 else list.maxOf { it.id } + 1
        list.add(data.copy(id = newId))
        saveAll(list)
    }

    // UPDATE - edit data berdasarkan id
    fun update(data: DataHujan) {
        val list = getAll()
        val index = list.indexOfFirst { it.id == data.id }
        if (index != -1) {
            list[index] = data
            saveAll(list)
        }
    }

    // DELETE - hapus data berdasarkan id
    fun delete(id: Int) {
        val list = getAll()
        list.removeAll { it.id == id }
        saveAll(list)
    }

    // Simpan list ke SharedPreferences
    private fun saveAll(list: MutableList<DataHujan>) {
        prefs.edit().putString(KEY, gson.toJson(list)).apply()
    }
}
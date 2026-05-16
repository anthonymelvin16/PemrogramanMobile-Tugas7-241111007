package com.utama.tugas5

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data")    val data: T?
)

data class DataHujanApi(
    @SerializedName("id")          val id: Int,
    @SerializedName("lokasi")      val lokasi: String,
    @SerializedName("tanggal")     val tanggal: String,
    @SerializedName("curah_hujan") val curahHujan: Double,
    @SerializedName("kategori")    val kategori: String,
    @SerializedName("keterangan")  val keterangan: String?
)

data class HujanRequest(
    @SerializedName("lokasi")      val lokasi: String,
    @SerializedName("tanggal")     val tanggal: String,
    @SerializedName("curah_hujan") val curahHujan: Double,
    @SerializedName("kategori")    val kategori: String,
    @SerializedName("keterangan")  val keterangan: String = ""
)
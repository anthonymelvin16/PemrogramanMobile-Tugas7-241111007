package com.utama.tugas5

import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("hujan.php")
    suspend fun getAllData(): Response<ApiResponse<List<DataHujanApi>>>

    @GET("hujan.php")
    suspend fun getDataById(
        @Query("id") id: Int
    ): Response<ApiResponse<DataHujanApi>>

    @POST("hujan.php")
    suspend fun addData(
        @Body request: HujanRequest
    ): Response<ApiResponse<Map<String, Int>>>

    @PUT("hujan.php")
    suspend fun updateData(
        @Query("id") id: Int,
        @Body request: HujanRequest
    ): Response<ApiResponse<Unit>>

    @DELETE("hujan.php")
    suspend fun deleteData(
        @Query("id") id: Int
    ): Response<ApiResponse<Unit>>
}
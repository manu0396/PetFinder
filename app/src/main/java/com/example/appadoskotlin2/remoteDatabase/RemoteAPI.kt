package com.example.appadoskotlin2.remoteDatabase

import com.example.appadoskotlin2.BuildConfig
import com.example.appadoskotlin2.data.ServiceResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RemoteAPI {
    companion object{
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
       // const val CLIENT_ID = BuildConfig.API_ACCES_KEY
    }

    @Headers("Accept-Version: v1")
    @GET("todos/")
    suspend fun searchService(
        @Query("query") query : String,
        @Query("page") page:Int, //Default 1
        @Query("per_page") itemCount: Int //Default 10
    ) : ServiceResponse
}
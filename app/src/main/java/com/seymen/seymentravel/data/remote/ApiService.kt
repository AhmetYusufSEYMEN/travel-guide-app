package com.seymen.seymentravel.data.remote

import com.seymen.seymentravel.domain.model.TravelModelItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("seymenapi/allList")
    suspend fun getTravelInfo(): List<TravelModelItem>
    @GET("seymenapi/allList/{detail_id}")
    suspend fun getTravelDetailsById(@Path("detail_id") detail_id:String) : List<TravelModelItem>

    @POST("seymenapi/allList")
    suspend fun updateTravelInfo(@Body isBookmarkPost: TravelModelItem): List<TravelModelItem>

}
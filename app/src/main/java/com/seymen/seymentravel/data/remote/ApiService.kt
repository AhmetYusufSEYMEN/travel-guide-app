package com.seymen.seymentravel.data.remote

import com.seymen.seymentravel.domain.model.TravelModelItem
import retrofit2.http.*

interface ApiService {

    @GET("seymenapi/allList")
    suspend fun getTravelInfo(): List<TravelModelItem>

    @GET("seymenapi/allList/{detail_id}")
    suspend fun getTravelDetailsById(@Path("detail_id") detail_id:String) : TravelModelItem

    @PUT("seymenapi/allList/{id}")
    suspend fun updateTravelInfo(@Body isBookmarkPost: TravelModelItem,@Path("id") id:String): TravelModelItem

}
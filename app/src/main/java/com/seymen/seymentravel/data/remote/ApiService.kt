package com.seymen.seymentravel.data.remote

import com.seymen.seymentravel.domain.model.GuideModelItem
import com.seymen.seymentravel.domain.model.TravelModelItem
import retrofit2.http.*

interface ApiService {

    @GET("seymenapi/allList")
    suspend fun getTravelInfo(): List<TravelModelItem>

    @GET("seymenapi/allList?category=flight|hotel|transportation")
    suspend fun getCategoryAllInfo(): List<TravelModelItem>

    @GET("seymenapi/allList?category=flight")
    suspend fun getCategoryFlightInfo(): List<TravelModelItem>

    @GET("seymenapi/allList?category=hotel")
    suspend fun getCategoryHotelInfo(): List<TravelModelItem>

    @GET("seymenapi/allList?category=transportation")
    suspend fun getCategoryTransportationInfo(): List<TravelModelItem>

    @GET("seymenapi/allList?category=topdestination")
    suspend fun getTopDestinationInfo(): List<TravelModelItem>

    @GET("seymenapi/allList?category=nearby")
    suspend fun getNearbyInfo(): List<TravelModelItem>

    @GET("seymenapi/allList?category=mightneed")
    suspend fun getMightNeedInfo(): List<TravelModelItem>

    @GET("seymenapi/allList?category=toppick")
    suspend fun getTopPickInfo(): List<TravelModelItem>

    @GET("seymenapi/allList?isBookmark=true")
    suspend fun getBookmarkTrueInfo(): List<TravelModelItem>

    @GET("seymenapi/allList?isTrip=true")
    suspend fun getTripTrueInfo(): List<TravelModelItem>

    @GET("seymenapi/allList/{detail_id}")
    suspend fun getTravelDetailsById(@Path("detail_id") detail_id:String) : TravelModelItem

    @PUT("seymenapi/allList/{id}")
    suspend fun updateTravelInfo(@Body isBookmarkPost: TravelModelItem,@Path("id") id:String): TravelModelItem

    @GET("seymenapi/guideCategories")
    suspend fun getGuideInfo(): List<GuideModelItem>

}
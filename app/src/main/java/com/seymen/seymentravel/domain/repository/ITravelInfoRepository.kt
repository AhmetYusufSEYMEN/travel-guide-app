package com.seymen.seymentravel.domain.repository

import com.seymen.seymentravel.domain.model.GuideModelItem
import com.seymen.seymentravel.domain.model.TravelModelItem

interface ITravelInfoRepository {

    suspend fun getTravelInfo(): List<TravelModelItem>

    suspend fun getCategoryAllInfo(): List<TravelModelItem>

    suspend fun getCategoryFlightInfo(): List<TravelModelItem>

    suspend fun getCategoryHoteltInfo(): List<TravelModelItem>

    suspend fun getCategoryTransportationInfo(): List<TravelModelItem>

    suspend fun getTopDestinationInfo(): List<TravelModelItem>

    suspend fun getNearbyInfo(): List<TravelModelItem>

    suspend fun getMightNeedInfo(): List<TravelModelItem>

    suspend fun getTopPickInfo(): List<TravelModelItem>

    suspend fun getBookmarkTrueInfo(): List<TravelModelItem>

    suspend fun getTripTrueInfo(): List<TravelModelItem>

    suspend fun getTravelInfoDetailsById(detailId: String): TravelModelItem

    suspend fun updateTravelInfo(isBookmarkPost: TravelModelItem, id: String): TravelModelItem

    suspend fun getGuideInfo(): List<GuideModelItem>

}
package com.seymen.seymentravel.data.remote.repository

import com.seymen.seymentravel.data.remote.ApiService
import com.seymen.seymentravel.domain.model.GuideModelItem
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.domain.repository.ITravelInfoRepository
import javax.inject.Inject

class ITravelInfoRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    ITravelInfoRepository {

    override suspend fun getTravelInfo(): List<TravelModelItem> = apiService.getTravelInfo()

    override suspend fun getCategoryAllInfo(): List<TravelModelItem> = apiService.getCategoryAllInfo()

    override suspend fun getCategoryFlightInfo(): List<TravelModelItem> = apiService.getCategoryFlightInfo()

    override suspend fun getCategoryHoteltInfo(): List<TravelModelItem> = apiService.getCategoryHotelInfo()

    override suspend fun getCategoryTransportationInfo(): List<TravelModelItem> = apiService.getCategoryTransportationInfo()

    override suspend fun getTopDestinationInfo(): List<TravelModelItem> = apiService.getTopDestinationInfo()

    override suspend fun getNearbyInfo(): List<TravelModelItem> = apiService.getNearbyInfo()

    override suspend fun getMightNeedInfo(): List<TravelModelItem> = apiService.getMightNeedInfo()

    override suspend fun getTopPickInfo(): List<TravelModelItem> = apiService.getTopPickInfo()

    override suspend fun getBookmarkTrueInfo(): List<TravelModelItem> = apiService.getBookmarkTrueInfo()

    override suspend fun getTripTrueInfo(): List<TravelModelItem> = apiService.getTripTrueInfo()

    override suspend fun getTravelInfoDetailsById(detailId: String): TravelModelItem = apiService.getTravelDetailsById(detailId)

    override suspend fun updateTravelInfo(isBookmarkPost: TravelModelItem, id: String): TravelModelItem = apiService.updateTravelInfo(isBookmarkPost, id)

    override suspend fun getGuideInfo(): List<GuideModelItem> = apiService.getGuideInfo()

}
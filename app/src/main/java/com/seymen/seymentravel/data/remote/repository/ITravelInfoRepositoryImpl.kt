package com.seymen.seymentravel.data.remote.repository

import com.seymen.seymentravel.data.remote.ApiService
import com.seymen.seymentravel.domain.model.GuideModelItem
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.domain.repository.ITravelInfoRepository
import javax.inject.Inject

class ITravelInfoRepositoryImpl @Inject constructor(private val apiService: ApiService) : ITravelInfoRepository {

    override suspend fun getTravelInfo(): List<TravelModelItem> {
        return apiService.getTravelInfo()
    }

    override suspend fun getCategoryAllInfo(): List<TravelModelItem> {
        return apiService.getCategoryAllInfo()
    }

    override suspend fun getCategoryFlightInfo(): List<TravelModelItem> {
        return apiService.getCategoryFlightInfo()
    }

    override suspend fun getCategoryHoteltInfo(): List<TravelModelItem> {
        return apiService.getCategoryHotelInfo()
    }

    override suspend fun getCategoryTransportationInfo(): List<TravelModelItem> {
        return apiService.getCategoryTransportationInfo()
    }

    override suspend fun getTopDestinationInfo(): List<TravelModelItem> {
        return apiService.getTopDestinationInfo()
    }

    override suspend fun getNearbyInfo(): List<TravelModelItem> {
        return apiService.getNearbyInfo()
    }

    override suspend fun getMightNeedInfo(): List<TravelModelItem> {
        return apiService.getMightNeedInfo()
    }

    override suspend fun getTopPickInfo(): List<TravelModelItem> {
        return apiService.getTopPickInfo()
    }

    override suspend fun getBookmarkTrueInfo(): List<TravelModelItem> {
        return apiService.getBookmarkTrueInfo()
    }

    override suspend fun getTripTrueInfo(): List<TravelModelItem> {
        return apiService.getTripTrueInfo()
    }


    override suspend fun getTravelInfoDetailsById(detailId: String): TravelModelItem {
        return apiService.getTravelDetailsById(detailId)
    }

    override suspend fun updateTravelInfo(isBookmarkPost: TravelModelItem, id:String): TravelModelItem {
        return apiService.updateTravelInfo(isBookmarkPost, id)
    }

    override suspend fun getGuideInfo(): List<GuideModelItem> {
        return apiService.getGuideInfo()
    }

}
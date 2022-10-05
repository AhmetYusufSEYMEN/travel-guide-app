package com.seymen.seymentravel.data.remote.repository

import com.seymen.seymentravel.data.remote.ApiService
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.domain.repository.ITravelInfoRepository
import javax.inject.Inject

class ITravelInfoRepositoryImpl @Inject constructor(private val apiService: ApiService) : ITravelInfoRepository {

    override suspend fun getTravelInfo(): List<TravelModelItem> {
        return apiService.getTravelInfo()
    }

    override suspend fun getTravelInfoDetailsById(detailId: String): List<TravelModelItem> {
        return apiService.getTravelDetailsById(detailId)
    }

    override suspend fun updateTravelInfo(isBookmarkPost: TravelModelItem, id:String): TravelModelItem {
        return apiService.updateTravelInfo(isBookmarkPost, id)
    }

}
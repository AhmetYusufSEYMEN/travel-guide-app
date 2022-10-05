package com.seymen.seymentravel.domain.repository

import com.seymen.seymentravel.domain.model.TravelModelItem

interface ITravelInfoRepository {

    suspend fun getTravelInfo(): List<TravelModelItem>
    suspend fun getTravelInfoDetailsById(detailId: String): List<TravelModelItem>
    suspend fun updateTravelInfo(isBookmarkPost: TravelModelItem): List<TravelModelItem>


}
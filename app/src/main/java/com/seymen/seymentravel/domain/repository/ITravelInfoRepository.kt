package com.seymen.seymentravel.domain.repository

import com.seymen.seymentravel.domain.model.GuideModelItem
import com.seymen.seymentravel.domain.model.TravelModelItem

interface ITravelInfoRepository {

    suspend fun getTravelInfo(): List<TravelModelItem>
    suspend fun getTravelInfoDetailsById(detailId: String): TravelModelItem
    suspend fun updateTravelInfo(isBookmarkPost: TravelModelItem, id:String): TravelModelItem
    suspend fun getGuideInfo(): List<GuideModelItem>

}
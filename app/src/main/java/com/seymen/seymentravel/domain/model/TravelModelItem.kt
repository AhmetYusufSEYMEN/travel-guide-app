package com.seymen.seymentravel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*@Parcelize
data class TravelModel(val list : List<TravelModelItem>) : Parcelable {
}
@Parcelize
data class TravelModelItem(
    val category: String,
    val city: String,
    val country: String,
    val description: String,
    val id: String,
    val travelImages: List<TravelImage>,
    val isBookmark: Boolean,
    val title: String
) :Parcelable

@Parcelize
data class TravelImage(
    val altText: String,
    val height: Int,
    val isHeroImage: Boolean,
    val url: String,
    val width: Int
) : Parcelable*/

@Parcelize
data class TravelModelItem(
    val category: String,
    val city: String,
    val country: String,
    val description: String,
    val id: String,
    val images: List<TravelImage>,
    val isBookmark: Boolean,
    val title: String
): Parcelable

@Parcelize
data class TravelImage(
    val altText: String,
    val height: Int,
    val isHeroImage: Boolean,
    val url: String,
    val width: Int
): Parcelable

@Parcelize
data class GuideCategories(
    val altText: String,
    val height: Int,
    val isHeroImage: Boolean,
    val url: String,
    val width: Int
): Parcelable

@Parcelize
data class GuideCategoriesItem(
    val icon: String,
    val id: String,
    val title: String
): Parcelable

package com.seymen.seymentravel.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TravelModelItem(
    @SerializedName("category")
    val category: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: List<TravelImage>,
    @SerializedName("isBookmark")
    var isBookmark: Boolean,
    @SerializedName("title")
    val title: String
): Parcelable

@Parcelize
data class TravelImage(
    @SerializedName("altText")
    val altText: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("isHeroImage")
    val isHeroImage: Boolean,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
): Parcelable

@Parcelize
data class GuideModelItem(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String
): Parcelable

package com.seymen.seymentravel.presentation.trip

interface IOnTripItemClickListener {
    fun onListItemClickListener(clickedId:String)
    fun onItemBookmarkClickListener(position:Int)
}
package com.seymen.seymentravel.presentation.guide

interface IOnGuideItemClickListener {
    fun onListItemClickListener(clickedId:String)
    fun onItemBookmarkClickListener(position:Int)
}
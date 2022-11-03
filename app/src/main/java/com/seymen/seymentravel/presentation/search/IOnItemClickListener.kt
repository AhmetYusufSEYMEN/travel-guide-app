package com.seymen.seymentravel.presentation.search

interface IOnItemClickListener {

    fun onListItemClickListener(clickedId: String)
    fun onItemBookmarkClickListener(position: Int)

}
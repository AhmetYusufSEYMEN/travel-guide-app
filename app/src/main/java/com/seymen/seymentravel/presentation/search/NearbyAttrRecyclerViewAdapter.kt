package com.seymen.seymentravel.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.seymen.seymentravel.BR
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.SearchNearbyRecyclerItemBinding
import com.seymen.seymentravel.domain.model.TravelModelItem

class NearbyAttrRecyclerViewAdapter (
    private var travelArrayList: List<TravelModelItem>,
    private val iOnItemClickListener: IOnItemClickListener
) : RecyclerView.Adapter<NearbyAttrRecyclerViewAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.search_nearby_recycler_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelArrayList[position])
        holder.binding.imgvSearchNearbyRecyclerview.setOnClickListener {
            iOnItemClickListener.onListItemClickListener(travelArrayList[position].id)
        }
        holder.binding.frmLayoutProgressbar.visibility = View.GONE

        holder.binding.imgBtnAddBookmark.setOnClickListener {
            holder.binding.frmLayoutProgressbar.visibility = View.VISIBLE
            iOnItemClickListener.onItemBookmarkClickListener(position)

        }

        when(travelArrayList[position].isBookmark){
            true -> holder.binding.imgBtnAddBookmark.background = ContextCompat.getDrawable(holder.binding.imgBtnAddBookmark.context, R.drawable.round_bookmark_checked)
            false -> holder.binding.imgBtnAddBookmark.background = ContextCompat.getDrawable(holder.binding.imgBtnAddBookmark.context, R.drawable.round_bookmark_unchecked)
        }
    }

    override fun getItemCount() = travelArrayList.size

    class ViewHolder (val binding: SearchNearbyRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(modelInfo : TravelModelItem){
            binding.setVariable(BR.nearbyAttrBinding,modelInfo)
            binding.executePendingBindings()
        }
    }
}
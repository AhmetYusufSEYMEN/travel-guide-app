package com.seymen.seymentravel.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.seymen.seymentravel.BR
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.DealRecyclerItemBinding
import com.seymen.seymentravel.domain.model.TravelModelItem

class TravelListRecyclerViewAdapter(
    private var travelArrayList: List<TravelModelItem>,
    private val iOnListItemClickListener: IOnListItemClickListener
) : RecyclerView.Adapter<TravelListRecyclerViewAdapter.ViewHolder> () {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.deal_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelArrayList[position])
        holder.binding.imgvHomeRecyclerview.setOnClickListener {
            iOnListItemClickListener.onListItemClickListener(travelArrayList[position].id)
        }

        holder.binding.imgBtnAddBookmark.setOnClickListener {
            iOnListItemClickListener.onItemBookmarkClickListener(position)
            when(travelArrayList[position].isBookmark){
                true -> holder.binding.imgBtnAddBookmark.background = ContextCompat.getDrawable(holder.binding.imgBtnAddBookmark.context, R.drawable.round_bookmark_checked)
                false -> holder.binding.imgBtnAddBookmark.background = ContextCompat.getDrawable(holder.binding.imgBtnAddBookmark.context, R.drawable.round_bookmark_unchecked)
            }
        }
    }

    override fun getItemCount() = travelArrayList.size

    class ViewHolder (val binding: DealRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(modelInfo : TravelModelItem){
            binding.setVariable(BR.dealRecyclerBinding,modelInfo)
            binding.executePendingBindings()
        }
    }
}
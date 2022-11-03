package com.seymen.seymentravel.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.seymen.seymentravel.BR
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.SearchResultItemBinding
import com.seymen.seymentravel.domain.model.TravelModelItem

class SearchResultRecyclerViewAdapter(
    private var searchInfoList: List<TravelModelItem>,
) : RecyclerView.Adapter<SearchResultRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.search_result_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchInfoList[position])
    }

    override fun getItemCount() = searchInfoList.size

    class ViewHolder(val binding: SearchResultItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modelInfo: TravelModelItem) {
            binding.setVariable(BR.searchResultBinding, modelInfo)
            binding.executePendingBindings()
        }
    }

}
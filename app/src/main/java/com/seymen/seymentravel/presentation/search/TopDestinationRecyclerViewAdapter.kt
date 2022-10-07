package com.seymen.seymentravel.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.seymen.seymentravel.BR
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.SearchDestinationRecyclerItemBinding
import com.seymen.seymentravel.domain.model.TravelModelItem


class TopDestinationRecyclerViewAdapter(
    private var travelArrayList: List<TravelModelItem>,
    private val iOnListItemClickListener: IOnItemClickListener
) : RecyclerView.Adapter<TopDestinationRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.search_destination_recycler_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelArrayList[position])
        holder.binding.root.setOnClickListener {
            iOnListItemClickListener.onListItemClickListener(travelArrayList[position].id)
        }
    }

    override fun getItemCount() = travelArrayList.size

    class ViewHolder(val binding: SearchDestinationRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modelInfo: TravelModelItem) {
            binding.setVariable(BR.destinationSearchBinding, modelInfo)
            binding.executePendingBindings()
        }
    }

}
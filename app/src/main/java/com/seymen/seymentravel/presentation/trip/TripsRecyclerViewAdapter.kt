package com.seymen.seymentravel.presentation.trip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.seymen.seymentravel.BR
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.TripsItemBinding
import com.seymen.seymentravel.domain.model.TravelModelItem

class TripsRecyclerViewAdapter(
    private var travelInfoList: List<TravelModelItem>,
    private val iOnTripItemClickListener: IOnTripItemClickListener
) : RecyclerView.Adapter<TripsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.trips_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelInfoList[position])
        holder.binding.root.setOnClickListener {
            iOnTripItemClickListener.onListItemClickListener(travelInfoList[position].id)
        }
        holder.binding.frmLayoutProgressbar.visibility = View.GONE

        holder.binding.imgBtnDeletetrip.setOnClickListener {
            holder.binding.frmLayoutProgressbar.visibility = View.VISIBLE
            iOnTripItemClickListener.onItemTripClickListener(position)
        }
    }

    override fun getItemCount() = travelInfoList.size

    class ViewHolder(val binding: TripsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modelInfo: TravelModelItem) {
            binding.setVariable(BR.tripsBinding, modelInfo)
            binding.executePendingBindings()
        }
    }
}
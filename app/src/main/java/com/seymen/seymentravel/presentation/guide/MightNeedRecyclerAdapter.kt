package com.seymen.seymentravel.presentation.guide

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.seymen.seymentravel.BR
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.MightNeedItemBinding
import com.seymen.seymentravel.domain.model.TravelModelItem

class MightNeedRecyclerAdapter(
    private var travelInfoList: List<TravelModelItem>,
    private val iOnGuideItemClickListener: IOnGuideItemClickListener
) : RecyclerView.Adapter<MightNeedRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.might_need_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelInfoList[position])
        holder.binding.root.setOnClickListener {
            iOnGuideItemClickListener.onListItemClickListener(travelInfoList[position].id)
        }
    }

    override fun getItemCount() = travelInfoList.size

    class ViewHolder(val binding: MightNeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modelInfo: TravelModelItem) {
            binding.setVariable(BR.mightNeedBinding, modelInfo)
            binding.executePendingBindings()
        }
    }
}
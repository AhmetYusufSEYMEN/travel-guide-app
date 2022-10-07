package com.seymen.seymentravel.presentation.guide

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.seymen.seymentravel.BR
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.CategoryItemBinding
import com.seymen.seymentravel.domain.model.GuideModelItem
import com.seymen.seymentravel.domain.model.TravelModelItem

class CategoryRecyclerAdapter(
    private var guideInfoList: List<GuideModelItem>,
    //private val iOnGuideItemClickListener: IOnGuideItemClickListener
) : RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.category_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(guideInfoList[position])
       /* holder.binding.root.setOnClickListener {
            iOnGuideItemClickListener.onListItemClickListener(guideInfoList[position].id)
        }*/
    }

    override fun getItemCount() = guideInfoList.size

    class ViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modelInfo: GuideModelItem) {
            binding.setVariable(BR.categoryBinding, modelInfo)
            binding.executePendingBindings()
        }
    }
}
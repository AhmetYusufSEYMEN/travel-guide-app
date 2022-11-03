package com.seymen.seymentravel.presentation.guide

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.seymen.seymentravel.BR
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.TopPickItemBinding
import com.seymen.seymentravel.domain.model.TravelModelItem

class TopPicksRecyclerAdapter(
    private var travelInfoList: List<TravelModelItem>,
    private val iOnGuideItemClickListener: IOnGuideItemClickListener
) : RecyclerView.Adapter<TopPicksRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.top_pick_item,
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
        holder.binding.frmLayoutProgressbar.visibility = View.GONE

        holder.binding.imgBtnAddBookmark.setOnClickListener {
            holder.binding.frmLayoutProgressbar.visibility = View.VISIBLE
            iOnGuideItemClickListener.onItemBookmarkClickListener(position)

        }

        when (travelInfoList[position].isBookmark) {
            true -> holder.binding.imgBtnAddBookmark.background = ContextCompat.getDrawable(
                holder.binding.imgBtnAddBookmark.context,
                R.drawable.round_bookmark_checked
            )
            false -> holder.binding.imgBtnAddBookmark.background = ContextCompat.getDrawable(
                holder.binding.imgBtnAddBookmark.context,
                R.drawable.round_bookmark_unchecked
            )
        }
    }

    override fun getItemCount() = travelInfoList.size

    class ViewHolder(val binding: TopPickItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modelInfo: TravelModelItem) {
            binding.setVariable(BR.topPickBinding, modelInfo)
            binding.executePendingBindings()
        }
    }
}
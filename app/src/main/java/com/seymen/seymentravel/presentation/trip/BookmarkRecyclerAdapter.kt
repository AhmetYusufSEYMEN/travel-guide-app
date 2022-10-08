package com.seymen.seymentravel.presentation.trip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.seymen.seymentravel.BR
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.BookmarkItemBinding
import com.seymen.seymentravel.domain.model.TravelModelItem

class BookmarkRecyclerAdapter (
    private var bookmarkInfoList: List<TravelModelItem>,
    private val iOnTripItemClickListener: IOnTripItemClickListener
) : RecyclerView.Adapter<BookmarkRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.bookmark_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookmarkInfoList[position])
        holder.binding.root.setOnClickListener {
            iOnTripItemClickListener.onListItemClickListener(bookmarkInfoList[position].id)
        }
        holder.binding.frmLayoutProgressbar.visibility = View.GONE

        holder.binding.imgBtnAddBookmark.setOnClickListener {
            holder.binding.frmLayoutProgressbar.visibility = View.VISIBLE
            iOnTripItemClickListener.onItemBookmarkClickListener(position)

        }

        when(bookmarkInfoList[position].isBookmark){
            true -> holder.binding.imgBtnAddBookmark.background = ContextCompat.getDrawable(holder.binding.imgBtnAddBookmark.context, R.drawable.round_bookmark_checked)
            false -> holder.binding.imgBtnAddBookmark.background = ContextCompat.getDrawable(holder.binding.imgBtnAddBookmark.context, R.drawable.round_bookmark_unchecked)
        }
    }

    override fun getItemCount() = bookmarkInfoList.size

    class ViewHolder(val binding: BookmarkItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modelInfo: TravelModelItem) {
            binding.setVariable(BR.bookmarkBinding, modelInfo)
            binding.executePendingBindings()
        }
    }
}
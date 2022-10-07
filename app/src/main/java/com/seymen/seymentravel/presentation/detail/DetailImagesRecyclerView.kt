package com.seymen.seymentravel.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.seymen.seymentravel.BR
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.DetailRecyclerItemBinding
import com.seymen.seymentravel.domain.model.TravelImage

class DetailImagesRecyclerView(
    private var travelImagesList: List<TravelImage>,
    private val iOnDetailClickListener: IOnDetailClickListener
) : RecyclerView.Adapter<DetailImagesRecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.detail_recycler_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(travelImagesList[position])
        holder.binding.root.setOnClickListener {
            iOnDetailClickListener.onImageClickListener(position)
        }
    }

    override fun getItemCount() = travelImagesList.size // 4 tane resim

    class ViewHolder (val binding: DetailRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind( image : TravelImage){
            binding.setVariable(BR.travelItemModel,image)
            binding.executePendingBindings()
        }
    }
}
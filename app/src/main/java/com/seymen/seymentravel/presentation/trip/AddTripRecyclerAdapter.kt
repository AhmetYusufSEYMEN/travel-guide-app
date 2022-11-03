package com.seymen.seymentravel.presentation.trip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.seymen.seymentravel.BR
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.AddTripItemBinding
import com.seymen.seymentravel.domain.model.TravelModelItem

class AddTripRecyclerAdapter(
    private var travelInfoList: List<TravelModelItem>,
    private val iOnTripItemClickListener: IOnTripItemClickListener
) : RecyclerView.Adapter<AddTripRecyclerAdapter.ViewHolder>() {
    private var holderList = ArrayList<ViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.add_trip_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelInfoList[position])
        if (holder !in holderList) holderList.add(holder)
        holder.binding.root.setOnClickListener {
            iOnTripItemClickListener.onItemTripClickListener(position)
            for (item in holderList) {
                item.binding.frmLayoutTickImg.visibility = View.GONE // tümü false edildi.
            }
            holder.binding.frmLayoutTickImg.visibility =
                View.VISIBLE  // sadece o anki tıklanılan item seçili olacak. diğerleri false oldu.
        }

        /*  if (travelInfoList[position].isTrip){
              holder.binding.frmLayoutTickImg.visibility = View.VISIBLE
          }*/

    }


    override fun getItemCount() = travelInfoList.size

    class ViewHolder(val binding: AddTripItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modelInfo: TravelModelItem) {
            binding.setVariable(BR.addTripBinding, modelInfo)
            binding.executePendingBindings()
        }
    }
}
package com.seymen.seymentravel.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentTransportationsBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.presentation.base.BaseFragment
import com.seymen.seymentravel.utils.AlertDialogHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransportationsFragment :
    BaseFragment<FragmentTransportationsBinding>(R.layout.fragment_transportations),
    IOnListItemClickListener {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var transportationList: ArrayList<TravelModelItem>
    private var updatedPosition = 0
    private lateinit var adapter: DealsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

    }

    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) {
            homeViewModel.getCategoryTransportationInfo()
            adapter.notifyItemChanged(updatedPosition)
        }
    }

    private fun setupObservers() {

        binding.transportationRcyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        homeViewModel.getCategoryTransportationInfo()

        homeViewModel.travelInfo.observe(viewLifecycleOwner) {

            transportationList = it as ArrayList<TravelModelItem>
            adapter = DealsRecyclerViewAdapter(transportationList, this)
            binding.transportationRcyclerView.adapter = adapter
        }
        homeViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        homeViewModel.isUpdateSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                transportationList.removeAt(updatedPosition)
                transportationList.add(updatedPosition, homeViewModel.itemUpdated.value!!)
                adapter.notifyItemChanged(updatedPosition) // refresh
            }
        }

        homeViewModel.errorState.observe(viewLifecycleOwner) {
            AlertDialogHelper.createSimpleAlertDialog(
                requireContext(),
                getString(R.string.error),
                it,
                resources.getString(R.string.positive_button_ok)
            )
        }
    }

    override fun onListItemClickListener(clickedId: String) {
        openDetailFragment(clickedId)
    }

    override fun onItemBookmarkClickListener(position: Int) {
        when (transportationList[position].isBookmark) {
            false -> transportationList[position].isBookmark = true
            true -> transportationList[position].isBookmark = false
        }
        updatedPosition = position
        homeViewModel.updateTravelInfo(transportationList[position])
    }

    private fun openDetailFragment(clickedId: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(clickedId)
        findNavController().navigate(action)
    }
}
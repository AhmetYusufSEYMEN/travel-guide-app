package com.seymen.seymentravel.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentHotelsBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.presentation.base.BaseFragment
import com.seymen.seymentravel.utils.AlertDialogHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotelsFragment : BaseFragment<FragmentHotelsBinding>(R.layout.fragment_hotels),
    IOnListItemClickListener {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var hotelList: ArrayList<TravelModelItem>
    private var updatedPosition = 0
    private lateinit var adapter: DealsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

    }

    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) {
            homeViewModel.getCategoryHoteltInfo()
            adapter.notifyItemChanged(updatedPosition)
        }
    }

    private fun setupObservers() {

        binding.hotelsRcyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        homeViewModel.getCategoryHoteltInfo()

        homeViewModel.travelInfo.observe(viewLifecycleOwner) {

            hotelList = it as ArrayList<TravelModelItem>
            adapter = DealsRecyclerViewAdapter(hotelList, this)
            binding.hotelsRcyclerView.adapter = adapter
        }

        homeViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        homeViewModel.isUpdateSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                hotelList.removeAt(updatedPosition)
                hotelList.add(updatedPosition, homeViewModel.itemUpdated.value!!)
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

        when (hotelList[position].isBookmark) {
            false -> hotelList[position].isBookmark = true
            true -> hotelList[position].isBookmark = false
        }
        homeViewModel.updateTravelInfo(hotelList[position])
    }

    private fun openDetailFragment(clickedId: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(clickedId)
        findNavController().navigate(action)
    }
}
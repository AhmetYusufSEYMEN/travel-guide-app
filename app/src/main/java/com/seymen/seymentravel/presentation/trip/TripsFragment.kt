package com.seymen.seymentravel.presentation.trip

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentTripsBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.presentation.base.BaseFragment
import com.seymen.seymentravel.utils.AlertDialogHelper
import com.seymen.seymentravel.utils.Constants
import com.seymen.seymentravel.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripsFragment : BaseFragment<FragmentTripsBinding>(R.layout.fragment_trips),
    IOnTripItemClickListener {

    private val tripsViewModel: TripViewModel by viewModels()
    private lateinit var tripsList: ArrayList<TravelModelItem>
    private lateinit var tripsAdapter: TripsRecyclerViewAdapter
    private var updatedPosition = 0
    private lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
        setupListeners()

    }

    override fun onResume() {
        super.onResume()
        if (::tripsAdapter.isInitialized) tripsAdapter.notifyItemChanged(updatedPosition)
    }

    private fun setupUI() {
        binding.rcyclTrips.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        sharedPreferencesUtils = SharedPreferencesUtils(requireActivity())

    }

    private fun setupListeners() {
        binding.floatingActionButton.setOnClickListener {
            val action = TripFragmentDirections.actionTripFragmentToAddTripFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupObservers() {

        tripsViewModel.getTripTrueInfo()

        tripsViewModel.isTripInfo.observe(viewLifecycleOwner) {

            tripsList = it as ArrayList<TravelModelItem>
            tripsAdapter = TripsRecyclerViewAdapter(tripsList, this)

            binding.rcyclTrips.adapter = tripsAdapter
        }

        tripsViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        tripsViewModel.errorState.observe(viewLifecycleOwner) {
            AlertDialogHelper.createSimpleAlertDialog(
                requireContext(), getString(R.string.error), it, resources.getString(
                    R.string.positive_button_ok
                )
            )
        }

        tripsViewModel.isUpdateSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                val processCheck =
                    sharedPreferencesUtils.readDataString(Constants.PROCESS_CHECK, "0")
                if (processCheck == "DELETE") {
                    tripsList.removeAt(updatedPosition)
                    tripsAdapter.notifyItemRemoved(updatedPosition)
                    tripsAdapter.notifyItemRangeChanged(updatedPosition, tripsList.size)
                }
            }
        }
    }

    override fun onListItemClickListener(clickedId: String) {}

    override fun onItemTripClickListener(position: Int) {

        tripsList[position].isTrip = false
        tripsList[position].startDate = ""
        tripsList[position].endDate = ""
        sharedPreferencesUtils.writeDataString(Constants.PROCESS_CHECK, "DELETE")

        updatedPosition = position
        Log.v("position12", "Click: $updatedPosition")
        tripsViewModel.updateTravelInfo(tripsList[position])
        tripsAdapter.notifyItemChanged(position)
    }
}
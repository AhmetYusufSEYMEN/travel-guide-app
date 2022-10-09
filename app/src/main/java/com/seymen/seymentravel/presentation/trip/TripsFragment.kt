package com.seymen.seymentravel.presentation.trip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentGuideBinding
import com.seymen.seymentravel.databinding.FragmentTripsBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.presentation.guide.*
import com.seymen.seymentravel.utils.AlertDialogHelper
import com.seymen.seymentravel.utils.NavBarHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripsFragment : Fragment(), IOnTripItemClickListener {

    private var _binding: FragmentTripsBinding? = null
    private val binding get() = _binding!!
    private val tripsViewModel: TripViewModel by viewModels()
    private lateinit var tripsList: ArrayList<TravelModelItem>
    private lateinit var tripsAdapter: TripsRecyclerViewAdapter
    private var updatedPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()

        binding.floatingActionButton.setOnClickListener {
            val action = TripFragmentDirections.actionTripFragmentToAddTripFragment()
            findNavController().navigate(action)
        }

    }

    private fun setupUI() {

        binding.rcyclTrips.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }
    private fun setupObservers() {

        tripsViewModel.getData()

        tripsViewModel.travelInfo.observe(viewLifecycleOwner) { it ->

            tripsList = it.filter { it.isTrip } as ArrayList<TravelModelItem>
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
                tripsList.removeAt(updatedPosition)
                tripsAdapter.notifyDataSetChanged() // refresh
            }
        }
    }

    override fun onListItemClickListener(clickedId: String) {

    }

    override fun onItemTripClickListener(position: Int) {

        tripsList[position].isTrip = false
        tripsList[position].startDate = ""
        tripsList[position].endDate = ""

        updatedPosition = position
        tripsViewModel.updateTravelInfo(tripsList[position])
        tripsAdapter.notifyItemChanged(position)

    }

    override fun onResume() {
        super.onResume()
        if (::tripsAdapter.isInitialized) tripsAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
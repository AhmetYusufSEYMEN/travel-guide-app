package com.seymen.seymentravel.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentTransportationsBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.utils.AlertDialogHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransportationsFragment : Fragment() ,IOnListItemClickListener {

    private var _binding: FragmentTransportationsBinding? = null
    private val binding get() = _binding!!
    private val allViewModel : AllViewModel by viewModels()
    private lateinit var  filterFlight: List<TravelModelItem>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransportationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

    }

    private fun setupObservers() {

        binding.transportationRcyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        allViewModel.getDealsInfo()

        allViewModel.travelInfo.observe(viewLifecycleOwner) { it ->

             filterFlight = it.filter { it.category == "transportation" }
            binding.transportationRcyclerView.adapter =
                DealsRecyclerViewAdapter(filterFlight, this)
        }
        allViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        allViewModel.errorState.observe(viewLifecycleOwner) {
            AlertDialogHelper.createSimpleAlertDialog(requireContext(), getString(R.string.error), it, resources.getString(R.string.positive_button_ok))
        }
    }

    override fun onListItemClickListener(clickedId: String) {
        openDetailFragment(clickedId)
    }

    override fun onItemBookmarkClickListener(position: Int) {
        Toast.makeText(requireContext(), "bookmark tıklandı id:${position+1}", Toast.LENGTH_SHORT).show()
        filterFlight[position].isBookmark = true
        allViewModel.updateTravelInfo(filterFlight[position])
    }

    private fun openDetailFragment(clickedId: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(clickedId)
        findNavController().navigate(action)

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
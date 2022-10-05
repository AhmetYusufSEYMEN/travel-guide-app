package com.seymen.seymentravel.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentAllBinding
import com.seymen.seymentravel.databinding.FragmentFlightsBinding
import com.seymen.seymentravel.utils.AlertDialogHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightsFragment : Fragment() , IOnListItemClickListener {

    private var _binding: FragmentFlightsBinding? = null
    private val binding get() = _binding!!
    private val allViewModel by viewModels<AllViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlightsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

    }

    private fun setupObservers() {

        binding.flightRcyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        allViewModel.getDealsInfo()

        allViewModel.travelInfo.observe(viewLifecycleOwner) { it ->

            val filterFlight = it.filter { it.category == "flight" }
            binding.flightRcyclerView.adapter = TravelListRecyclerViewAdapter(filterFlight,this)
        }
        allViewModel.loadingState.observe(viewLifecycleOwner){ isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        allViewModel.errorState.observe(viewLifecycleOwner){
            AlertDialogHelper.createSimpleAlertDialog(requireContext(),getString(R.string.error),it,resources.getString(R.string.positive_button_ok))
        }

    }
    override fun onListItemClickListener(clickedId: String) {
        openDetailFragment(clickedId)
    }

    override fun onItemBookmarkClickListener(position: Int) {
        TODO("Not yet implemented") // KENDİME NOT:
                                            // BUNLARI YOK ETMEK İÇİN BOOKMARK I GERÇEKTEN YAPMAYA GEÇTİĞİNDE:
                                            // BOOKMARK OLAN SAYFALARA ÖZGÜ CLİCK LİSTENER AÇARSIN BURADAKİNDEN BKMRK İNTRFCSİNDEN BU KISMI SİLERSİN
    }

    private fun openDetailFragment(clickedId:String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(clickedId)
        findNavController().navigate(action)

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
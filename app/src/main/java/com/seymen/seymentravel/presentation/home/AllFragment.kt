package com.seymen.seymentravel.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentAllBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.utils.AlertDialogHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllFragment : Fragment(), IOnListItemClickListener {

    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel : HomeViewModel by viewModels()
    private lateinit var allList: ArrayList<TravelModelItem>
    private var updatedPosition = 0
    private  lateinit var adapter: DealsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

    }

    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized){
            homeViewModel.getCategoryAllInfo()
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupObservers() {

        binding.allRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        homeViewModel.getCategoryAllInfo()

        homeViewModel.travelInfo.observe(viewLifecycleOwner) {

            allList = it as ArrayList<TravelModelItem>
            adapter = DealsRecyclerViewAdapter(allList, this)
            binding.allRecyclerView.adapter = adapter

        }

        homeViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        homeViewModel.isUpdateSuccess.observe(viewLifecycleOwner)  { isSuccess ->
            if (isSuccess){
                allList.removeAt(updatedPosition)
                allList.add(updatedPosition,homeViewModel.itemUpdated.value!!)
                adapter.notifyDataSetChanged() // refresh
            }
        }

        homeViewModel.errorState.observe(viewLifecycleOwner) {
            AlertDialogHelper.createSimpleAlertDialog(requireContext(), getString(R.string.error), it, resources.getString(R.string.positive_button_ok))
        }

    }

    override fun onListItemClickListener(clickedId: String) {
        openDetailFragment(clickedId)
    }

    override fun onItemBookmarkClickListener(position: Int) {
        when(allList[position].isBookmark){
             false->allList[position].isBookmark = true
             true ->allList[position].isBookmark = false
         }
        updatedPosition = position
        homeViewModel.updateTravelInfo(allList[position])
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




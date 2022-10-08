package com.seymen.seymentravel.presentation.search

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentSearchBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.presentation.guide.GuideFragmentDirections
import com.seymen.seymentravel.utils.AlertDialogHelper
import com.seymen.seymentravel.utils.ConnectionCheckHelper
import com.seymen.seymentravel.utils.NavBarHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() , IOnItemClickListener {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel : SearchViewModel by viewModels()
    private lateinit var searchTopDestList: ArrayList<TravelModelItem>
    private lateinit var searchNearbyList: ArrayList<TravelModelItem>
    private var updatedPosition = 0
    private  lateinit var topDestadapter: TopDestinationRecyclerViewAdapter
    private  lateinit var nearbyAttradapter: NearbyAttrRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavBarHelper.navBarIsVisible(requireActivity())

        activity?.let { ConnectionCheckHelper.checkNetAndClose(requireContext(),it) }

        setupObservers()

        binding.edtxSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(binding.edtxSearch.text.toString().lowercase()) //.lowercase()
                findNavController().navigate(action)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

    }

    private fun setupObservers() {

        binding.rcyclvTopDestination.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcyclvNearbyAttr.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        searchViewModel.getData()

        searchViewModel.travelInfo.observe(viewLifecycleOwner) { it ->

            searchTopDestList = it.filter { it.category == "topdestination" } as ArrayList<TravelModelItem>
            topDestadapter = TopDestinationRecyclerViewAdapter(searchTopDestList, this)

            searchNearbyList = it.filter { it.category =="nearby" } as ArrayList<TravelModelItem>
            nearbyAttradapter = NearbyAttrRecyclerViewAdapter(searchNearbyList,this)


            binding.rcyclvTopDestination.adapter = topDestadapter
            binding.rcyclvNearbyAttr.adapter = nearbyAttradapter

            binding.swipe.isRefreshing = false

        }

        searchViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.swipe.isRefreshing = false
        }

        searchViewModel.errorState.observe(viewLifecycleOwner) {
            AlertDialogHelper.createSimpleAlertDialog(requireContext(), getString(R.string.error), it, resources.getString(
                R.string.positive_button_ok))
            binding.swipe.isRefreshing = false
        }

        searchViewModel.isUpdateSuccess.observe(viewLifecycleOwner)  { isSuccess ->
            if (isSuccess){
                searchNearbyList.removeAt(updatedPosition)
                searchNearbyList.add(updatedPosition,searchViewModel.itemUpdated.value!!)
                nearbyAttradapter.notifyDataSetChanged() // refresh
                binding.swipe.isRefreshing = false
            }
        }
        binding.swipe.setOnRefreshListener {
            searchViewModel.getData()
        }
    }

    override fun onListItemClickListener(clickedId: String) {
        openDetailFragment(clickedId)
    }

    private fun openDetailFragment(clickedId: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(clickedId)
        findNavController().navigate(action)
    }

    override fun onItemBookmarkClickListener(position: Int) {
        when(searchNearbyList[position].isBookmark){
            false->searchNearbyList[position].isBookmark = true
            true ->searchNearbyList[position].isBookmark = false
        }
        updatedPosition = position
        searchViewModel.updateTravelInfo(searchNearbyList[position])
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
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
import com.seymen.seymentravel.databinding.FragmentBookmarkBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.presentation.guide.GuideFragmentDirections
import com.seymen.seymentravel.utils.AlertDialogHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment(), IOnTripItemClickListener{

    private var _binding : FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private val tripViewModel : TripViewModel by viewModels()
    private lateinit var bookmarkList: ArrayList<TravelModelItem>
    private var updatedPosition = 0
    private  lateinit var bookmarkAdapter: BookmarkRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcyclBookmark.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        setupObservers()

    }

    private fun setupObservers() {

        tripViewModel.getBookmarkTrueInfo()

        tripViewModel.isBookmarkInfo.observe(viewLifecycleOwner) {

            bookmarkList = it as ArrayList<TravelModelItem>

            if(bookmarkList.isNotEmpty()){
                bookmarkAdapter = BookmarkRecyclerAdapter(bookmarkList,this)
                binding.rcyclBookmark.adapter = bookmarkAdapter
            }
            else{

                binding.imgvNotFound.visibility = View.VISIBLE
                binding.rcyclBookmark.visibility = View.GONE
            }

        }

        tripViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

        }

        tripViewModel.errorState.observe(viewLifecycleOwner) {
            AlertDialogHelper.createSimpleAlertDialog(requireContext(), getString(R.string.error), it, resources.getString(
                R.string.positive_button_ok))
        }

        tripViewModel.isUpdateSuccess.observe(viewLifecycleOwner)  { isSuccess ->
            if (isSuccess){
                bookmarkList.removeAt(updatedPosition)
                bookmarkAdapter.notifyDataSetChanged() // refresh
            }
        }
    }

    override fun onListItemClickListener(clickedId: String) {
    }

    override fun onItemTripClickListener(position: Int) {
        when(bookmarkList[position].isBookmark){
            false->bookmarkList[position].isBookmark = true
            true ->bookmarkList[position].isBookmark = false
        }
        updatedPosition = position
        tripViewModel.updateTravelInfo(bookmarkList[position])
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
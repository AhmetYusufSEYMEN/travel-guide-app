package com.seymen.seymentravel.presentation.trip

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentBookmarkBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.presentation.base.BaseFragment
import com.seymen.seymentravel.utils.AlertDialogHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(R.layout.fragment_bookmark),
    IOnTripItemClickListener {

    private val tripViewModel: TripViewModel by viewModels()
    private lateinit var bookmarkList: ArrayList<TravelModelItem>
    private var updatedPosition = 0
    private lateinit var bookmarkAdapter: BookmarkRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcyclBookmark.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        setupObservers()

    }

    private fun setupObservers() {

        tripViewModel.getBookmarkTrueInfo()

        tripViewModel.isBookmarkInfo.observe(viewLifecycleOwner) {

            bookmarkList = it as ArrayList<TravelModelItem>

            if (bookmarkList.isNotEmpty()) {
                bookmarkAdapter = BookmarkRecyclerAdapter(bookmarkList, this)
                binding.rcyclBookmark.adapter = bookmarkAdapter
            } else {

                binding.imgvNotFound.visibility = View.VISIBLE
                binding.rcyclBookmark.visibility = View.GONE
            }
        }

        tripViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

        }

        tripViewModel.errorState.observe(viewLifecycleOwner) {
            AlertDialogHelper.createSimpleAlertDialog(
                requireContext(), getString(R.string.error), it, resources.getString(
                    R.string.positive_button_ok
                )
            )
        }

        tripViewModel.isUpdateSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                bookmarkList.removeAt(updatedPosition)
                bookmarkAdapter.notifyItemRemoved(updatedPosition) // refresh
            }
        }
    }

    override fun onListItemClickListener(clickedId: String) {
    }

    override fun onItemTripClickListener(position: Int) {
        when (bookmarkList[position].isBookmark) {
            false -> bookmarkList[position].isBookmark = true
            true -> bookmarkList[position].isBookmark = false
        }
        updatedPosition = position
        tripViewModel.updateTravelInfo(bookmarkList[position])
    }
}
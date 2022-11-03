package com.seymen.seymentravel.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentDetailBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.presentation.base.BaseFragment
import com.seymen.seymentravel.utils.AlertDialogHelper
import com.seymen.seymentravel.utils.NavBarHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail),
    IOnDetailClickListener {

    private val detailsViewModel: DetailsViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var travelDetailID: String
    private lateinit var travelModelItem: TravelModelItem
    private lateinit var imageUrl: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setInfoObserver()
        setupListeners()

    }

    private fun setupUI() {
        NavBarHelper.navBarVisibleGoner(requireActivity())
        travelDetailID = args.detailID

    }

    private fun setupListeners() {

        binding.apply {
            backButton.setOnClickListener {
                findNavController().navigateUp()
            }

            btnAddBookmark.setOnClickListener {
                setBookmark()
            }
            enlargeButton.setOnClickListener {
                val action =
                    DetailFragmentDirections.actionDetailFragmentToFullSizeImageFragment(imageUrl)
                findNavController().navigate(action)
            }
        }
    }

    private fun setInfoObserver() {

        binding.detailImageRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        detailsViewModel.getTravelInfoDetailsById(travelDetailID)

        detailsViewModel.travelDetailInfo.observe(viewLifecycleOwner) {

            travelModelItem = it
            imageUrl = travelModelItem.images[0].url
            binding.travelDetailBinding = travelModelItem
            binding.travelBigImage = travelModelItem.images[0].url
            binding.executePendingBindings()

            if (it.isBookmark) {
                binding.btnAddBookmark.visibility = View.GONE
            }

            binding.detailImageRecyclerView.adapter = DetailImagesRecyclerView(it.images, this)

        }

        detailsViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

        }

        detailsViewModel.errorState.observe(viewLifecycleOwner) {
            AlertDialogHelper.createSimpleAlertDialog(
                requireContext(), getString(R.string.error), it, resources.getString(
                    R.string.positive_button_ok
                )
            )
        }

        detailsViewModel.isUpdateSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                binding.progressBar.visibility = View.GONE
                binding.btnAddBookmark.visibility = View.GONE
                binding.imgvIsBookmark.setImageResource(R.drawable.checked_favourite_icon)
            }
        }
    }

    private fun setBookmark() {
        travelModelItem.isBookmark = true
        detailsViewModel.updateTravelInfo(travelModelItem)
        binding.progressBar.visibility = View.VISIBLE

    }


    override fun onImageClickListener(clickedPosition: Int) {
        setImage(clickedPosition)
    }

    private fun setImage(clickedPosition: Int) {
        binding.travelBigImage = travelModelItem.images[clickedPosition].url
        imageUrl = travelModelItem.images[clickedPosition].url
    }
}
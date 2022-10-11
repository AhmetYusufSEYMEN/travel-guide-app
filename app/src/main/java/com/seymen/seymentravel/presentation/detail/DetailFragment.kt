package com.seymen.seymentravel.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.MainActivity
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentDetailBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.utils.AlertDialogHelper
import com.seymen.seymentravel.utils.ConnectionCheckHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() ,IOnDetailClickListener{

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private val detailsViewModel: DetailsViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var travelDetailID : String
    private lateinit var  travelModelItem: TravelModelItem



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupUI()
        setInfoObserver()
        setupListeners()

    }

    private fun setupUI() {
        mainActivity = requireActivity() as MainActivity
        mainActivity.bottomNavigationView.visibility = View.GONE

        travelDetailID = args.detailID

        activity?.let { ConnectionCheckHelper.checkNetAndClose(requireContext(),it) }
    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnAddBookmark.setOnClickListener {
            setBookmark()
        }
    }

    private fun setInfoObserver() {

        binding.detailImageRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        detailsViewModel.getTravelInfoDetailsById(travelDetailID)

        detailsViewModel.travelDetailInfo.observe(viewLifecycleOwner) {

            travelModelItem = it
            binding.travelDetailBinding = travelModelItem
            binding.travelBigImage = travelModelItem.images[0].url
            binding.executePendingBindings()

            if (it.isBookmark){
                binding.btnAddBookmark.visibility = View.GONE
            }

            binding.detailImageRecyclerView.adapter = DetailImagesRecyclerView(it.images,this)

        }

        detailsViewModel.loadingState.observe(viewLifecycleOwner){ isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

        }

        detailsViewModel.errorState.observe(viewLifecycleOwner){
            AlertDialogHelper.createSimpleAlertDialog(requireContext(),getString(R.string.error),it,resources.getString(
                R.string.positive_button_ok))
        }

        detailsViewModel.isUpdateSuccess.observe(viewLifecycleOwner)  { isSuccess ->
            if (isSuccess){
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


    override fun onImageClickListener(clickedPosition:Int) {
        setImage(clickedPosition)
    }

    private fun setImage(clickedPosition:Int){
        binding.travelBigImage = travelModelItem.images[clickedPosition].url
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
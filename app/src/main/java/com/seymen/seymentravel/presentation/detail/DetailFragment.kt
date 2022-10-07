package com.seymen.seymentravel.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.MainActivity
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentDetailBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.presentation.home.AllViewModel
import com.seymen.seymentravel.utils.AlertDialogHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() ,IOnDetailClickListener{

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private val detailsViewModel: DetailsViewModel by viewModels()
    private val allDataViewModel: AllViewModel by viewModels()
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

        mainActivity = requireActivity() as MainActivity
        mainActivity.bottomNavigationView.visibility = View.GONE

        travelDetailID = args.detailID

        setInfoObserver()
        //setRecyclerObserver()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
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

            binding.detailImageRecyclerView.adapter = DetailImagesRecyclerView(it.images,this)

        }

        detailsViewModel.loadingState.observe(viewLifecycleOwner){ isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

        }

        detailsViewModel.errorState.observe(viewLifecycleOwner){
            AlertDialogHelper.createSimpleAlertDialog(requireContext(),getString(R.string.error),it,resources.getString(
                R.string.positive_button_ok))
        }
    }


    override fun onImageClickListener(clickedPosition:Int) {
        setImage(clickedPosition)
    }

    private fun setImage(clickedPosition:Int){

        Toast.makeText(requireContext(), ""+clickedPosition, Toast.LENGTH_SHORT).show()
        binding.travelBigImage = travelModelItem.images[clickedPosition].url
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
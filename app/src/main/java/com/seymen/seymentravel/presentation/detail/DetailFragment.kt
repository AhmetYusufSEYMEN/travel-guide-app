package com.seymen.seymentravel.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.seymen.seymentravel.MainActivity
import com.seymen.seymentravel.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private val detailsViewModel: DetailsViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var travelDetail : String

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
        travelDetail = args.detailID
        Log.v("TAGG",travelDetail)
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
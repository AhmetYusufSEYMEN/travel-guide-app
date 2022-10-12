package com.seymen.seymentravel.presentation.detail

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.seymen.seymentravel.MainActivity
import com.seymen.seymentravel.databinding.FragmentFullSizeImageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullSizeImageFragment : Fragment() {

    private var _binding: FragmentFullSizeImageBinding? = null
    private val binding get() = _binding!!
    private val args: FullSizeImageFragmentArgs by navArgs()
    private lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullSizeImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageUrlStr = args.imageUrl

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        mainActivity = requireActivity() as MainActivity
        mainActivity.bottomNavigationView.visibility = View.GONE
        setupListeners()
    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        _binding = null
        super.onDestroyView()
    }

}
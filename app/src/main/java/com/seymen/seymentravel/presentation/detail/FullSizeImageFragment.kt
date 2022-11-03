package com.seymen.seymentravel.presentation.detail

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentFullSizeImageBinding
import com.seymen.seymentravel.presentation.base.BaseFragment
import com.seymen.seymentravel.utils.NavBarHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullSizeImageFragment :
    BaseFragment<FragmentFullSizeImageBinding>(R.layout.fragment_full_size_image) {
    private val args: FullSizeImageFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageUrlStr = args.imageUrl

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        NavBarHelper.navBarVisibleGoner(requireActivity())
        setupListeners()
    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        super.onDestroyView()
    }

}
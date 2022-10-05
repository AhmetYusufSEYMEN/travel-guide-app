package com.seymen.seymentravel.presentation.guide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.seymen.seymentravel.MainActivity
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentGuideBinding
import com.seymen.seymentravel.utils.DataBindingAdapters
import com.seymen.seymentravel.utils.NavBarHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuideFragment : Fragment() {

    private var _binding : FragmentGuideBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuideBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavBarHelper.navBarIsVisible(requireActivity())
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
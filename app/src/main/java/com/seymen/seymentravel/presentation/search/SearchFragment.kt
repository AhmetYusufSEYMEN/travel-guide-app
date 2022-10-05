package com.seymen.seymentravel.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.Navigation
import com.seymen.seymentravel.MainActivity
import com.seymen.seymentravel.databinding.FragmentAllBinding

import com.seymen.seymentravel.databinding.FragmentSearchBinding
import com.seymen.seymentravel.utils.DataBindingAdapters
import com.seymen.seymentravel.utils.NavBarHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
          Navigation.findNavController(view).popBackStack()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
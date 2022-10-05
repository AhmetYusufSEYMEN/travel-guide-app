package com.seymen.seymentravel.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentHomeBinding
import com.seymen.seymentravel.utils.NavBarHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavBarHelper.navBarIsVisible(requireActivity())
        setViewPager()
        //setupObservers()

    }

   /* private fun setupObservers() {
        homeViewModel.getTravelInfo().observe(requireActivity()) {
            it?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        binding.recyclerMars.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { informations -> retrieveList(informations) }
                    }
                    Resource.Status.ERROR -> {
                        binding.recyclerMars.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Resource.Status.LOADING -> {
                        binding.recyclerMars.visibility = View.VISIBLE
                        binding.recyclerMars.visibility = View.GONE
                    }
                }
            }
        }
    }*/

    private fun setViewPager() {

        val tabTitles = arrayOf(getString(R.string.all),getString(R.string.flights),getString(R.string.hotels),getString(R.string.transportation))

        binding.apply {
            viewPagerHome.adapter = DealsViewPagerAdapter(childFragmentManager,lifecycle)
            viewPagerHome.isUserInputEnabled = false
            TabLayoutMediator(tabLayoutHome,viewPagerHome){
                    tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
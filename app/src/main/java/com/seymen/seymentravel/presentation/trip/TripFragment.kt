package com.seymen.seymentravel.presentation.trip


import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentTripBinding
import com.seymen.seymentravel.presentation.base.BaseFragment
import com.seymen.seymentravel.utils.NavBarHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripFragment : BaseFragment<FragmentTripBinding>(R.layout.fragment_trip) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavBarHelper.navBarVisibilityChanger(requireActivity())
        setViewPager()
    }

    private fun setViewPager() {
        val tabTitles = arrayOf(getString(R.string.trips), getString(R.string.bookmark))
        val tabIcons = arrayOf(
            ContextCompat.getDrawable(requireContext(), R.drawable.nav_bar_black_trip),
            ContextCompat.getDrawable(requireContext(), R.drawable.bookmark)
        )

        binding.apply {
            viewPagetrip.adapter = TripViewPagerAdapter(childFragmentManager, lifecycle)
            viewPagetrip.isUserInputEnabled = false
            TabLayoutMediator(tabLayoutTrip, viewPagetrip) { tab, position ->
                tab.text = tabTitles[position]
                tab.icon = tabIcons[position]
            }.attach()
        }
    }
}
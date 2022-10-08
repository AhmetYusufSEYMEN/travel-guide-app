package com.seymen.seymentravel.presentation.trip


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.tabs.TabLayoutMediator
import com.seymen.seymentravel.MainActivity
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentTripBinding
import com.seymen.seymentravel.utils.ConnectionCheckHelper
import com.seymen.seymentravel.utils.DataBindingAdapters
import com.seymen.seymentravel.utils.NavBarHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TripFragment : Fragment() {

    private var _binding : FragmentTripBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavBarHelper.navBarIsVisible(requireActivity())
        activity?.let { ConnectionCheckHelper.checkNetAndClose(requireContext(),it) }

        setViewPager()
    }

    private fun setViewPager() {
        val tabTitles = arrayOf(getString(R.string.trips),getString(R.string.bookmark))
        val tabIcons = arrayOf(ContextCompat.getDrawable(requireContext(), R.drawable.nav_bar_black_trip),ContextCompat.getDrawable(requireContext(), R.drawable.bookmark))

        binding.apply {
            viewPagetrip.adapter = TripViewPagerAdapter(childFragmentManager,lifecycle)
            viewPagetrip.isUserInputEnabled = false
            TabLayoutMediator(tabLayoutTrip,viewPagetrip){
                    tab, position ->
                tab.text = tabTitles[position]
                tab.icon = tabIcons[position]
            }.attach()
        }
    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
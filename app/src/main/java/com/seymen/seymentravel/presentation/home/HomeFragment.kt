package com.seymen.seymentravel.presentation.home

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.browser.customtabs.CustomTabsIntent
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
        setupListeners()

    }

    private fun setupListeners() {
        binding.apply {
            btnBannerflights.setOnClickListener {
                openChromeTab(requireContext(), "https://www.youtube.com/watch?v=1DMAOBco7Mc")
            }
            btnBannerhotels.setOnClickListener {
                openChromeTab(requireContext(), "https://www.youtube.com/watch?v=wi-UD2n6Fbs")
            }
            btnBannerCars.setOnClickListener {
                openChromeTab(requireContext(), "https://www.youtube.com/watch?v=kEtlNKro7Bo")
            }
            btnBannerTaxi.setOnClickListener {
                openChromeTab(requireContext(), "https://www.youtube.com/watch?v=7TxUbHa3x28")
            }
        }
    }


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

    private fun openChromeTab(context: Context, urlD:String){
        val builder = CustomTabsIntent.Builder()
        builder.setStartAnimations(context, R.anim.slide_in, R.anim.slide_out);
        builder.setExitAnimations(context, R.anim.fade_in, R.anim.fade_out);

        builder.setToolbarColor(ContextCompat.getColor(context,R.color.pink))
        builder.addDefaultShareMenuItem()
        builder.setShowTitle(true)
        builder.enableUrlBarHiding()

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(urlD))

    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
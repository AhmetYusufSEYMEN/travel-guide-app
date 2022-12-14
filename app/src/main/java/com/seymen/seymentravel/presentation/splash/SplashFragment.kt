package com.seymen.seymentravel.presentation.splash

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentSplashBinding
import com.seymen.seymentravel.presentation.base.BaseFragment
import com.seymen.seymentravel.utils.Constants.ONBOARDING_SP_KEY
import com.seymen.seymentravel.utils.NavBarHelper
import com.seymen.seymentravel.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavBarHelper.navBarVisibleGoner(requireActivity())
        hideBar()
        animation()
    }

    private fun animation() {
        val slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.side_slide)
        binding.imgvSplash.startAnimation(slideAnimation)

        slideAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                sharedPreferencesUtils = SharedPreferencesUtils(requireActivity())
                when (sharedPreferencesUtils.readDataString(ONBOARDING_SP_KEY, "0")) {
                    "0" -> findNavController().navigate(R.id.action_splashFragment_to_onBoardFragment)
                    else -> findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    private fun hideBar() {
        if (Build.VERSION.SDK_INT < 30) {
            requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            requireActivity().actionBar?.hide()
        } else {
            requireActivity().window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
        }
    }
}
package com.seymen.seymentravel.presentation.onboard

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.navigation.fragment.findNavController
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentOnBoardBinding
import com.seymen.seymentravel.presentation.base.BaseFragment
import com.seymen.seymentravel.utils.Constants.ONBOARDING_SP_KEY
import com.seymen.seymentravel.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardFragment : BaseFragment<FragmentOnBoardBinding>(R.layout.fragment_on_board) {

    private lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferencesUtils = SharedPreferencesUtils(requireActivity())
        showBar()
        initListeners()
    }

    private fun initListeners() {

        binding.btnLetsGo.setOnClickListener {
            sharedPreferencesUtils.writeDataString(ONBOARDING_SP_KEY, "1")
            findNavController().navigate(R.id.action_onBoardFragment_to_homeFragment)
        }
    }

    private fun showBar() {
        if (Build.VERSION.SDK_INT < 30) {
            requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            requireActivity().actionBar?.show()
        } else {
            requireActivity().window.decorView.windowInsetsController?.show(WindowInsets.Type.statusBars())
        }
    }
}
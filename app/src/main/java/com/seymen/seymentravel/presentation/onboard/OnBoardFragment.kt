package com.seymen.seymentravel.presentation.onboard

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.navigation.fragment.findNavController
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentOnBoardBinding
import com.seymen.seymentravel.utils.Constants.ONBOARDING_SP_KEY
import com.seymen.seymentravel.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardFragment : Fragment() {

    private var _binding : FragmentOnBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferencesUtils = SharedPreferencesUtils(requireActivity())
        showBar()
        initListeners()
    }

    private fun initListeners() {

        binding.btnLetsGo.setOnClickListener {
            sharedPreferencesUtils.writeDataString(ONBOARDING_SP_KEY,"1")
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
package com.seymen.seymentravel.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.seymen.seymentravel.utils.ConnectionCheckHelper

open class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) : Fragment() {

    private var _binding: T? = null
    val binding: T get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { ConnectionCheckHelper.checkNetAndClose(requireContext(), it) }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
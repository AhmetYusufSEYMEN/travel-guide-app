package com.seymen.seymentravel.presentation.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentSearchResultBinding
import com.seymen.seymentravel.presentation.base.BaseFragment
import com.seymen.seymentravel.utils.AlertDialogHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {

    private val searchViewModel: SearchViewModel by viewModels()
    private val args: SearchResultFragmentArgs by navArgs()
    private lateinit var searchingWords: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchingWords = args.source
        binding.rcyclResult.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        setupObservers()
        setupListeners()

    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupObservers() {

        if (searchingWords == "ComingFromSeeAll") {

            searchViewModel.getMightNeedInfo()
            searchViewModel.mightNeedInfo.observe(viewLifecycleOwner) {

                binding.txvTitle.text = getString(R.string.see_all)

                binding.rcyclResult.adapter = SearchResultRecyclerViewAdapter(it)
            }
        } else {
            searchViewModel.getAllInfo()
            searchViewModel.allDataInfo.observe(viewLifecycleOwner) { it ->

                val filterSearchingWords = it.filter { it.country.lowercase() == searchingWords }
                binding.txvTitle.text = getString(R.string.result)
                if (filterSearchingWords.isNotEmpty()) {
                    binding.rcyclResult.adapter =
                        SearchResultRecyclerViewAdapter(filterSearchingWords)
                } else {
                    binding.txvTitle.text = getString(R.string.no_result)
                    binding.imgvNotFound.visibility = View.VISIBLE
                    binding.rcyclResult.visibility = View.GONE
                }
            }
        }

        searchViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

        }

        searchViewModel.errorState.observe(viewLifecycleOwner) {
            AlertDialogHelper.createSimpleAlertDialog(
                requireContext(), getString(R.string.error), it, resources.getString(
                    R.string.positive_button_ok
                )
            )
        }
    }
}
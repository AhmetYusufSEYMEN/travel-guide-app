package com.seymen.seymentravel.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentDetailBinding
import com.seymen.seymentravel.databinding.FragmentSearchResultBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.utils.AlertDialogHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private var _binding : FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel : SearchViewModel by viewModels()
    private lateinit var searchList : ArrayList<TravelModelItem>
    private val args: SearchResultFragmentArgs by navArgs()
    private lateinit var searchingWords : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchingWords = args.source

        setupObservers()

    }

    private fun setupObservers() {

        binding.rcyclResult.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        searchViewModel.getData()

        searchViewModel.travelInfo.observe(viewLifecycleOwner) { it ->

            if (searchingWords == "ComingFromSeeAll"){
                binding.txvTitle.text = getString(R.string.see_all)
                val filterMightNeed = it.filter { it.category == "mightneed" }
                binding.rcyclResult.adapter = SearchResultRecyclerViewAdapter(filterMightNeed)
            }
            else {
                val filterSearchingWords = it.filter { it.country.lowercase() == searchingWords } //lowercase()
                    binding.txvTitle.text = getString(R.string.result)
                    if(filterSearchingWords.isNotEmpty()){
                        binding.rcyclResult.adapter = SearchResultRecyclerViewAdapter(filterSearchingWords)
                    }
                    else{
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
            AlertDialogHelper.createSimpleAlertDialog(requireContext(), getString(R.string.error), it, resources.getString(
                R.string.positive_button_ok))
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
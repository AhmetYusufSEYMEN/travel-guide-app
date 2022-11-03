package com.seymen.seymentravel.presentation.guide

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentGuideBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.presentation.base.BaseFragment
import com.seymen.seymentravel.utils.AlertDialogHelper
import com.seymen.seymentravel.utils.NavBarHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuideFragment : BaseFragment<FragmentGuideBinding>(R.layout.fragment_guide),
    IOnGuideItemClickListener {

    private val guideViewModel: GuideViewModel by viewModels()
    private lateinit var mightNeedList: ArrayList<TravelModelItem>
    private lateinit var topPickList: ArrayList<TravelModelItem>
    private var updatedPosition = 0
    private lateinit var mightNeedAdapter: MightNeedRecyclerAdapter
    private lateinit var categoryAdapter: CategoryRecyclerAdapter
    private lateinit var topPickAdapter: TopPicksRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
        setupListeners()

    }

    private fun setupUI() {
        NavBarHelper.navBarVisibilityChanger(requireActivity())
        binding.seeAll.paint?.isUnderlineText = true
        binding.rcyclvMightNeed.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcyclvCategory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcyclvTopPick.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupListeners() {

        binding.seeAll.setOnClickListener {
            val action =
                GuideFragmentDirections.actionGuideFragmentToSearchResultFragment("ComingFromSeeAll")
            findNavController().navigate(action)

        }

        binding.edtxSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val action = GuideFragmentDirections.actionGuideFragmentToSearchResultFragment(
                    binding.edtxSearch.text.toString().lowercase()
                ) //.lowercase()
                findNavController().navigate(action)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun setupObservers() {

        guideViewModel.getMightNeedInfo()
        guideViewModel.mightNeedInfo.observe(viewLifecycleOwner) { mightneed ->

            mightNeedList = mightneed as ArrayList<TravelModelItem>
            mightNeedAdapter = MightNeedRecyclerAdapter(mightNeedList, this)

            binding.rcyclvMightNeed.adapter = mightNeedAdapter

            binding.swipe.isRefreshing = false
        }

        guideViewModel.getTopPickInfo()
        guideViewModel.topPickInfo.observe(viewLifecycleOwner) { toppick ->

            topPickList = toppick as ArrayList<TravelModelItem>
            topPickAdapter = TopPicksRecyclerAdapter(topPickList, this)

            binding.rcyclvTopPick.adapter = topPickAdapter

            binding.swipe.isRefreshing = false
        }

        guideViewModel.getGuideInfo()
        guideViewModel.guideInfo.observe(viewLifecycleOwner) {

            categoryAdapter = CategoryRecyclerAdapter(it)

            binding.rcyclvCategory.adapter = categoryAdapter

            binding.swipe.isRefreshing = false

        }

        guideViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.swipe.isRefreshing = false
        }

        guideViewModel.errorState.observe(viewLifecycleOwner) {
            AlertDialogHelper.createSimpleAlertDialog(
                requireContext(), getString(R.string.error), it, resources.getString(
                    R.string.positive_button_ok
                )
            )
            binding.swipe.isRefreshing = false
        }

        guideViewModel.isUpdateSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                topPickList.removeAt(updatedPosition)
                topPickList.add(updatedPosition, guideViewModel.itemUpdated.value!!)
                topPickAdapter.notifyItemChanged(updatedPosition) // refresh
                binding.swipe.isRefreshing = false
            }
        }
        binding.swipe.setOnRefreshListener {
            guideViewModel.getMightNeedInfo()
            guideViewModel.getTopPickInfo()
            guideViewModel.getGuideInfo()

        }
    }


    override fun onListItemClickListener(clickedId: String) {
        openDetailFragment(clickedId)
    }

    private fun openDetailFragment(clickedId: String) {
        val action = GuideFragmentDirections.actionGuideFragmentToDetailFragment(clickedId)
        findNavController().navigate(action)
    }

    override fun onItemBookmarkClickListener(position: Int) {

        when (topPickList[position].isBookmark) {
            false -> topPickList[position].isBookmark = true
            true -> topPickList[position].isBookmark = false
        }

        updatedPosition = position
        guideViewModel.updateTravelInfo(topPickList[position])

    }
}
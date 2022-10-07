package com.seymen.seymentravel.presentation.guide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentGuideBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.utils.AlertDialogHelper
import com.seymen.seymentravel.utils.NavBarHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuideFragment : Fragment() , IOnGuideItemClickListener {

    private var _binding : FragmentGuideBinding? = null
    private val binding get() = _binding!!
    private val guideViewModel by viewModels<GuideViewModel>()
    private lateinit var mightNeedList: ArrayList<TravelModelItem>
    private lateinit var topPickList: ArrayList<TravelModelItem>
    private var updatedPosition = 0
    private  lateinit var mightNeedAdapter: MightNeedRecyclerAdapter
    private  lateinit var categoryAdapter: CategoryRecyclerAdapter
    private  lateinit var topPickAdapter: TopPicksRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuideBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavBarHelper.navBarIsVisible(requireActivity())

        setupObservers()

        binding.seeAll.paint?.isUnderlineText = true
        binding.seeAll.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.coming_soon), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservers() {

        binding.rcyclvMightNeed.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcyclvCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcyclvTopPick.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        guideViewModel.getData()


        guideViewModel.travelInfo.observe(viewLifecycleOwner) { it ->

            mightNeedList = it.filter { it.category == "mightneed" } as ArrayList<TravelModelItem>
            mightNeedAdapter = MightNeedRecyclerAdapter(mightNeedList, this)

            topPickList = it.filter { it.category =="toppick" } as ArrayList<TravelModelItem>
            topPickAdapter = TopPicksRecyclerAdapter(topPickList,this)

            binding.rcyclvMightNeed.adapter = mightNeedAdapter

            binding.rcyclvTopPick.adapter = topPickAdapter

        }
        guideViewModel.getGuideInfo()
        guideViewModel.guideInfo.observe(viewLifecycleOwner) {

             categoryAdapter = CategoryRecyclerAdapter(it)

            binding.rcyclvCategory.adapter = categoryAdapter


        }



        guideViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

        }

        guideViewModel.errorState.observe(viewLifecycleOwner) {
            AlertDialogHelper.createSimpleAlertDialog(requireContext(), getString(R.string.error), it, resources.getString(
                R.string.positive_button_ok))
        }

        guideViewModel.isUpdateSuccess.observe(viewLifecycleOwner)  { isSuccess ->
            if (isSuccess){
                topPickList.removeAt(updatedPosition)
                topPickList.add(updatedPosition,guideViewModel.itemUpdated.value!!)
                topPickAdapter.notifyDataSetChanged() // refresh
            }
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
        when(topPickList[position].isBookmark){
            false->topPickList[position].isBookmark = true
            true ->topPickList[position].isBookmark = false
        }
        updatedPosition = position
        guideViewModel.updateTravelInfo(topPickList[position])
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
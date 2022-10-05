package com.seymen.seymentravel.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentAllBinding
import com.seymen.seymentravel.utils.AlertDialogHelper

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.merge

@AndroidEntryPoint
class AllFragment : Fragment() , IOnListItemClickListener{

    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!
    private val allViewModel by viewModels<AllViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        allViewModel.getDealsInfo()

        allViewModel.travelInfo.observe(viewLifecycleOwner) { it ->
            val allList = it.filter { it.category == "flight" || it.category == "hotel" || it.category == "transportation" }

            val filterHotel = it.filter { it.category == "hotel" }
            val filterTransportation = it.filter { it.category == "transportation" }

            binding.allRecyclerView.adapter = TravelListRecyclerViewAdapter(allList,this)
        }
        allViewModel.loadingState.observe(viewLifecycleOwner){ isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        allViewModel.errorState.observe(viewLifecycleOwner){
            AlertDialogHelper.createSimpleAlertDialog(requireContext(),getString(R.string.error),it,resources.getString(R.string.positive_button_ok))
        }

        //setupUI()
        //setupObservers()
    }

    override fun onListItemClickListener(clickedId: String) {
        openDetailFragment(clickedId)
    }
    private fun openDetailFragment(clickedId:String){
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(clickedId)
        findNavController().navigate(action)


    /*  val bundle = Bundle()
        bundle.putString(Constants.BUNDLE_DETAIL_ID_KEY, clickedMovieId)
        findNavController().navigate(R.id.action_movieListFragment_to_movieDetailsFragment, bundle)*/
    }
    /* private fun setupUI() {
         /**
          * recycler adapter setted
          */
         binding.allRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         adapter = AllRecyclerViewAdapter(arrayListOf()){ item ->

            /* val mars = MarsModel(item.id,item.img_src,item.price,item.type)
             val action = MarsListFragmentDirections.actionMarsListFragment2ToMarsDetailsFragment(mars)
             findNavController().navigate(action)*/
         }
         binding.allRecyclerView.addItemDecoration(
             DividerItemDecoration(
                 binding.allRecyclerView.context,
                 (binding.allRecyclerView.layoutManager as LinearLayoutManager).orientation
             )
         )
         binding.allRecyclerView.adapter = adapter
     }*/

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}




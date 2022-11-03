package com.seymen.seymentravel.presentation.trip

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seymen.seymentravel.R
import com.seymen.seymentravel.databinding.FragmentAddTripBinding
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.presentation.base.BaseFragment
import com.seymen.seymentravel.utils.AlertDialogHelper
import com.seymen.seymentravel.utils.Constants
import com.seymen.seymentravel.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddTripFragment : BaseFragment<FragmentAddTripBinding>(R.layout.fragment_add_trip),
    IOnTripItemClickListener {

    private val tripsViewModel: TripViewModel by viewModels()
    private lateinit var tripsList: ArrayList<TravelModelItem>
    private lateinit var selectedTravelModelItem: TravelModelItem
    private lateinit var tripsAdapter: AddTripRecyclerAdapter
    private var cal = Calendar.getInstance()
    private lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
        setupListeners()

    }

    private fun setupListeners() {
        binding.edtxStartDate.setOnClickListener {
            if (::selectedTravelModelItem.isInitialized) {
                DatePickerDialog(
                    requireContext(),
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, monthOfYear)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                        val myFormat = "EEE, MMM d, ''yy"
                        val sdf = SimpleDateFormat(myFormat, Locale("tr", "TR"))
                        binding.edtxStartDate.setText(sdf.format(cal.time).toString())
                        selectedTravelModelItem.startDate = binding.edtxStartDate.text.toString()

                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.choose_first_location),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.edtxFinishDate.setOnClickListener {
            if (::selectedTravelModelItem.isInitialized) {
                DatePickerDialog(
                    requireContext(),
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, monthOfYear)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                        val myFormat = "EEE, MMM d, ''yy"
                        val sdf = SimpleDateFormat(myFormat, Locale("tr", "TR"))
                        binding.edtxFinishDate.setText(sdf.format(cal.time).toString())
                        selectedTravelModelItem.endDate = binding.edtxFinishDate.text.toString()

                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.choose_first_location),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        // update
        binding.btnAdd.setOnClickListener {
            if (binding.tvSelectedTripName.text.toString().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.be_sure_enter_info),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                selectedTravelModelItem.isTrip = true
                tripsViewModel.updateTravelInfo(selectedTravelModelItem)
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupUI() {

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        sharedPreferencesUtils = SharedPreferencesUtils(requireActivity())

    }

    private fun setupObservers() {

        tripsViewModel.getAllInfo()

        tripsViewModel.travelInfo.observe(viewLifecycleOwner) { travelList ->

            tripsList = travelList as ArrayList<TravelModelItem>
            tripsAdapter = AddTripRecyclerAdapter(travelList, this)
            binding.recyclerView.adapter = tripsAdapter
            binding.recyclerView.setItemViewCacheSize(travelList.size)

        }

        tripsViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

        }

        tripsViewModel.errorState.observe(viewLifecycleOwner) {
            AlertDialogHelper.createSimpleAlertDialog(
                requireContext(), getString(R.string.error), it, resources.getString(
                    R.string.positive_button_ok
                )
            )
        }

        tripsViewModel.isUpdateSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                sharedPreferencesUtils.writeDataString(Constants.PROCESS_CHECK, "ADD")

                findNavController().navigateUp()
            }
        }
    }

    override fun onListItemClickListener(clickedId: String) {
    }

    override fun onItemTripClickListener(position: Int) {
        selectedTravelModelItem = tripsList[position]
        binding.tvSelectedTripName.text = selectedTravelModelItem.title
    }
}

package com.seymen.seymentravel.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.domain.usecase.TravelInfoUseCase
import com.seymen.seymentravel.utils.Resource
import com.seymen.seymentravel.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dealsUseCase: TravelInfoUseCase,
) : ViewModel() {

    //cached
    private val _travelInfo = MutableLiveData<List<TravelModelItem>>()
    val itemUpdated = MutableLiveData<TravelModelItem>()

    //public
    val travelInfo: MutableLiveData<List<TravelModelItem>> = _travelInfo

    val loadingState = MutableLiveData<Boolean>()
    val isUpdateSuccess = MutableLiveData<Boolean>()
    val errorState = SingleLiveEvent<String?>()

    fun getData() {
        viewModelScope.launch {
            dealsUseCase.getTravelInfo().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        loadingState.value = true
                    }
                    is Resource.Success -> {
                        loadingState.value = false
                        _travelInfo.value = result.data!!
                    }
                    is Resource.Error -> {
                        loadingState.value = false
                        errorState.value = result.message
                    }
                }
            }
        }
    }

    fun updateTravelInfo(isBookmarkPost: TravelModelItem) {
        viewModelScope.launch {
            dealsUseCase.updateBookMarkStatus(isBookmarkPost).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        loadingState.value = true
                    }
                    is Resource.Success -> {
                        loadingState.value = false
                        itemUpdated.value = result.data!!
                        isUpdateSuccess.value = true
                    }
                    is Resource.Error -> {
                        loadingState.value = false
                        isUpdateSuccess.value = false
                        errorState.value = result.message
                    }
                }
            }
        }
    }

}
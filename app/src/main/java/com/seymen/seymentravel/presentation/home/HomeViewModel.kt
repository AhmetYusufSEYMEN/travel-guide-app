package com.seymen.seymentravel.presentation.home

import androidx.lifecycle.LiveData
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
class HomeViewModel @Inject constructor(
    private val travelInfoUseCase: TravelInfoUseCase,
) : ViewModel() {

    //cached
    private val _travelInfo = MutableLiveData<List<TravelModelItem>>()

    //public
    val travelInfo: MutableLiveData<List<TravelModelItem>> = _travelInfo

    private val _itemUpdated = MutableLiveData<TravelModelItem>()
    val itemUpdated: LiveData<TravelModelItem> = _itemUpdated

    val loadingState = MutableLiveData<Boolean>()
    val isUpdateSuccess = MutableLiveData<Boolean>()
    val errorState = SingleLiveEvent<String?>()

    fun getCategoryAllInfo() {
        viewModelScope.launch {
            travelInfoUseCase.getCategoryAllInfo().collect { result ->
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

    fun getCategoryFlightInfo() {
        viewModelScope.launch {
            travelInfoUseCase.getCategoryFlightInfo().collect { result ->
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

    fun getCategoryHoteltInfo() {
        viewModelScope.launch {
            travelInfoUseCase.getCategoryHoteltInfo().collect { result ->
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

    fun getCategoryTransportationInfo() {
        viewModelScope.launch {
            travelInfoUseCase.getCategoryTransportationInfo().collect { result ->
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
            travelInfoUseCase.updateWhatYouWant(isBookmarkPost).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        loadingState.value = true
                    }
                    is Resource.Success -> {
                        loadingState.value = false
                        _itemUpdated.value = result.data!!
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
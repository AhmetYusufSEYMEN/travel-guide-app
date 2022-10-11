package com.seymen.seymentravel.presentation.trip

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
class TripViewModel @Inject constructor(
    private val travelInfoUseCase: TravelInfoUseCase
) : ViewModel() {

    //cached
    private val _travelInfo = MutableLiveData<List<TravelModelItem>>()
    //public
    val travelInfo: MutableLiveData<List<TravelModelItem>> = _travelInfo

    private val _isBookmarkInfo = MutableLiveData<List<TravelModelItem>>()
    val isBookmarkInfo: MutableLiveData<List<TravelModelItem>> = _isBookmarkInfo

    private val _isTripInfo = MutableLiveData<List<TravelModelItem>>()
    val isTripInfo: MutableLiveData<List<TravelModelItem>> = _isTripInfo

    private val _itemUpdated = MutableLiveData<TravelModelItem>()
    val itemUpdated : MutableLiveData<TravelModelItem> = _itemUpdated

    val loadingState = MutableLiveData<Boolean>()
    val isUpdateSuccess = MutableLiveData<Boolean>()
    val errorState = SingleLiveEvent<String?>()

    fun getAllInfo() {
        viewModelScope.launch {
            travelInfoUseCase.getAllInfo().collect { result ->
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

    fun getBookmarkTrueInfo() {
        viewModelScope.launch {
            travelInfoUseCase.getBookmarkTrueInfo().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        loadingState.value = true
                    }
                    is Resource.Success -> {
                        loadingState.value = false
                        _isBookmarkInfo.value = result.data!!
                    }
                    is Resource.Error -> {
                        loadingState.value = false
                        errorState.value = result.message
                    }
                }
            }
        }
    }

    fun getTripTrueInfo() {
        viewModelScope.launch {
            travelInfoUseCase.getTripTrueInfo().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        loadingState.value = true
                    }
                    is Resource.Success -> {
                        loadingState.value = false
                        _isTripInfo.value = result.data!!
                    }
                    is Resource.Error -> {
                        loadingState.value = false
                        errorState.value = result.message
                    }
                }
            }
        }
    }

    fun updateTravelInfo(isTrip: TravelModelItem) {
        viewModelScope.launch {
            travelInfoUseCase.updateWhatYouWant(isTrip).collect { result ->
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
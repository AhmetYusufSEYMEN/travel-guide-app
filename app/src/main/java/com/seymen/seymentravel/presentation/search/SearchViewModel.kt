package com.seymen.seymentravel.presentation.search

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
class SearchViewModel @Inject constructor(
    private val travelInfoUseCase: TravelInfoUseCase,
) : ViewModel() {

    private val _allDataInfo = MutableLiveData<List<TravelModelItem>>()
    val allDataInfo: LiveData<List<TravelModelItem>> = _allDataInfo

    private val _mightNeedInfo = MutableLiveData<List<TravelModelItem>>()
    val mightNeedInfo: LiveData<List<TravelModelItem>> = _mightNeedInfo

    private val _topDestInfo = MutableLiveData<List<TravelModelItem>>()
    val topDestInfo: LiveData<List<TravelModelItem>> = _topDestInfo

    private val _nearByInfo = MutableLiveData<List<TravelModelItem>>()
    val nearByInfo: LiveData<List<TravelModelItem>> = _nearByInfo

    private val _itemUpdated = MutableLiveData<TravelModelItem>()
    val itemUpdated: LiveData<TravelModelItem> = _itemUpdated

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
                        _allDataInfo.value = result.data!!
                    }
                    is Resource.Error -> {
                        loadingState.value = false
                        errorState.value = result.message
                    }
                }
            }
        }
    }

    fun getMightNeedInfo() {
        viewModelScope.launch {
            travelInfoUseCase.getMightNeedInfo().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        loadingState.value = true
                    }
                    is Resource.Success -> {
                        loadingState.value = false
                        _mightNeedInfo.value = result.data!!
                    }
                    is Resource.Error -> {
                        loadingState.value = false
                        errorState.value = result.message
                    }
                }
            }
        }
    }

    fun getTopDestinationInfo() {
        viewModelScope.launch {
            travelInfoUseCase.getTopDestinationInfo().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        loadingState.value = true
                    }
                    is Resource.Success -> {
                        loadingState.value = false
                        _topDestInfo.value = result.data!!
                    }
                    is Resource.Error -> {
                        loadingState.value = false
                        errorState.value = result.message
                    }
                }
            }
        }
    }

    fun getNearbyInfo() {
        viewModelScope.launch {
            travelInfoUseCase.getNearbyInfo().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        loadingState.value = true
                    }
                    is Resource.Success -> {
                        loadingState.value = false
                        _nearByInfo.value = result.data!!
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
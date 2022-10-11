package com.seymen.seymentravel.presentation.guide

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymen.seymentravel.domain.model.GuideModelItem
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.domain.usecase.TravelInfoUseCase
import com.seymen.seymentravel.utils.Resource
import com.seymen.seymentravel.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuideViewModel  @Inject constructor(
    private val travelInfoUseCase: TravelInfoUseCase,
) : ViewModel() {

    private val _topPickInfo = MutableLiveData<List<TravelModelItem>>()
    val topPickInfo: MutableLiveData<List<TravelModelItem>> = _topPickInfo

    private val _mightNeedInfo = MutableLiveData<List<TravelModelItem>>()
    val mightNeedInfo: MutableLiveData<List<TravelModelItem>> = _mightNeedInfo

    private val _itemUpdated = MutableLiveData<TravelModelItem>()
    val itemUpdated : MutableLiveData<TravelModelItem> = _itemUpdated

    private val _guideInfo = MutableLiveData<List<GuideModelItem>>()
    val guideInfo : MutableLiveData<List<GuideModelItem>> = _guideInfo


    val loadingState = MutableLiveData<Boolean>()
    val isUpdateSuccess = MutableLiveData<Boolean>()
    val errorState = SingleLiveEvent<String?>()



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

    fun getTopPickInfo() {
        viewModelScope.launch {
            travelInfoUseCase.getTopPickInfo().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        loadingState.value = true
                    }
                    is Resource.Success -> {
                        loadingState.value = false
                        _topPickInfo.value = result.data!!
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

    fun getGuideInfo() {
        viewModelScope.launch {
            travelInfoUseCase.getGuideInfo().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        loadingState.value = true
                    }
                    is Resource.Success -> {
                        loadingState.value = false
                        _guideInfo.value = result.data!!
                    }
                    is Resource.Error -> {
                        loadingState.value = false
                        errorState.value = result.message
                    }
                }
            }
        }
    }

}
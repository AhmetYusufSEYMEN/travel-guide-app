package com.seymen.seymentravel.presentation.home

import android.util.Log
import android.util.MutableBoolean
import androidx.lifecycle.*
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.domain.repository.ITravelInfoRepository
import com.seymen.seymentravel.domain.usecase.TravelInfoUseCase
import com.seymen.seymentravel.utils.Resource
import com.seymen.seymentravel.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllViewModel @Inject constructor(
    private val dealsUseCase : TravelInfoUseCase,
    private val iTravelInfoRepository: ITravelInfoRepository
) : ViewModel() {

    //cached
    private val _travelInfo =MutableLiveData<List<TravelModelItem>>()

    //public
    val travelInfo : MutableLiveData<List<TravelModelItem>> = _travelInfo
    val bookmarkState = MutableLiveData<Boolean>()
    val loadingState = MutableLiveData<Boolean>()
    val errorState = SingleLiveEvent<String?>()

    fun getDealsInfo() {
        viewModelScope.launch {
            dealsUseCase.getTravelInfo().collect{ result ->
                when (result) {
                    is Resource.Loading -> {
                        loadingState.value = true
                    }
                    is Resource.Success -> {
                        loadingState.value = false
                        _travelInfo.value = result.data!!
                        bookmarkState.value  = result.data[0].isBookmark
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
            iTravelInfoRepository.updateTravelInfo(isBookmarkPost,isBookmarkPost.id)
        }
    }
}
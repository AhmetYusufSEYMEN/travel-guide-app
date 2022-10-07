package com.seymen.seymentravel.presentation.detail

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
class DetailsViewModel @Inject constructor(
    private val dealsUseCase : TravelInfoUseCase
) : ViewModel() {
    //cached
    private val _travelDetailInfo = MutableLiveData<TravelModelItem>()
    //public
    val travelDetailInfo : MutableLiveData<TravelModelItem> = _travelDetailInfo

    val loadingState = MutableLiveData<Boolean>()
    val errorState = SingleLiveEvent<String?>()

    fun getTravelInfoDetailsById(detailId: String) {
        viewModelScope.launch {
            dealsUseCase.getTravelInfoDetailsById(detailId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        loadingState.value = true
                    }
                    is Resource.Success -> {
                        loadingState.value = false
                        _travelDetailInfo.value = result.data!!
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
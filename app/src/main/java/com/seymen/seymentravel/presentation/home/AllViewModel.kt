package com.seymen.seymentravel.presentation.home

import androidx.lifecycle.*
import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.domain.usecase.TravelInfoUseCase
import com.seymen.seymentravel.utils.Resource
import com.seymen.seymentravel.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllViewModel @Inject constructor(
    private val dealsUseCase : TravelInfoUseCase
) : ViewModel() {
    //cached
    private val _travelInfo =MutableLiveData<List<TravelModelItem>>()
    //public
    val travelInfo : MutableLiveData<List<TravelModelItem>> = _travelInfo

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
                    }
                    is Resource.Error -> {
                        loadingState.value = false
                        errorState.value = result.message
                    }
                }
            }
        }
    }

    /*//cached
    private val _travelInfo = MutableLiveData<Resource<TravelModel>>()
    //public
    val travelInfo : LiveData<Resource<TravelModel>> get() =  _travelInfo*/

    /*
    fun getAllInfo() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = travelInfoRepositoryImpl.getTravelInfo()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }*/


  /*  fun getTravelInfo() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = travelInfoUseCase.invoke()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }*/

  //do login
  /*fun doLogin(travelModel: TravelModel) = viewModelScope.launch {
          try {
              _travelInfo.value = travelInfoUseCase.getTravelInfo()
          }
          catch (exception: Exception){

          }
      }*/

   /* private fun getCoins() {
        travelInfoUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                   // _travelInfo = InfoState(infos = result.data ?: emptyList())
                    emit(InfoState(infos =result.data))
                }
                is Resource.Error -> {
                    //_travelInfo = InfoState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    //_travelInfo = InfoState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }*/

    /*fun getTravelInfoo() : LiveData<TravelModel> {
            travelInfoUseCase.apply {
                getTravelInfo()
                return travelInfo
            }
        }

    fun getTravelInfo() = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = travelInfoUseCase.getTravelInfo()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }*/
}
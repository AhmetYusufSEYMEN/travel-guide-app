package com.seymen.seymentravel.domain.usecase


import com.seymen.seymentravel.domain.model.TravelModelItem
import com.seymen.seymentravel.domain.repository.ITravelInfoRepository
import com.seymen.seymentravel.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TravelInfoUseCase @Inject constructor(
    private val iTravelInfoRepository: ITravelInfoRepository
) {

    suspend fun getTravelInfo(): Flow<Resource<List<TravelModelItem>>> = flow {
        try {
            val movies = iTravelInfoRepository.getTravelInfo()

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(movies))

        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage))
        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage))
        }
    }

    suspend fun getTravelInfoDetailsById(detailId:String): Flow<Resource<List<TravelModelItem>>> = flow {
        try {
            val movies = iTravelInfoRepository.getTravelInfoDetailsById(detailId)
            // loading
            emit(Resource.Loading())
            emit(Resource.Success(movies))

        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage))
        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage))
        }
    }

    /*fun getTravelInfo(): Flow<Resource<List<TravelModel>>> = flow {
        emit(Resource.Loading)
        val resource =
            try {
                val response = travelInfoRepository.getTravelInfo()
                Resource.Success(response)
            } catch (e: Throwable) {
                Resource.Fail(e)
            }
        emit(resource)
    }*/
   /* PHİLP
    operator fun invoke(): Flow<Resource<List<TravelModel>>> = flow {
        try {
            emit(Resource.Loading<List<TravelModel>>())
            val repo = travelInfoRepository.getTravelInfo()
            emit(Resource.Success<List<TravelModel>>(repo))
        } catch(e: HttpException) {
            emit(Resource.Error<List<TravelModel>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<TravelModel>>("Couldn't reach server. Check your internet connection."))
        }
    }
    */
    /*private var _travelInfo = MutableLiveData<TravelModel>()
    val travelInfo : LiveData<TravelModel> = _travelInfo

     fun getTravelInfo(){
        travelInfoRepository.getTravelInfo().enqueue(object: Callback<TravelModel> {
            override fun onResponse(call: Call<TravelModel>, response: Response<TravelModel>) {
                _travelInfo.value = response.body()
            }

            override fun onFailure(call: Call<TravelModel>, t: Throwable) {
                Log.v("TAG",t.message.toString())
            }

        })
    }*/
}
package com.seymen.seymentravel.domain.usecase


import android.util.Log
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
            val data = iTravelInfoRepository.getTravelInfo()

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage))
        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage))
        }
    }

    suspend fun getTravelInfoDetailsById(detailId:String): Flow<Resource<TravelModelItem>> = flow {
        try {
            val data = iTravelInfoRepository.getTravelInfoDetailsById(detailId)
            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage))
        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage))
        }
    }

    suspend fun updateBookMarkStatus(isBookmarkPost: TravelModelItem): Flow<Resource<TravelModelItem>> = flow {
        try {
            val data = iTravelInfoRepository.updateTravelInfo(isBookmarkPost,isBookmarkPost.id)

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage))
        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage))
        }
    }
}
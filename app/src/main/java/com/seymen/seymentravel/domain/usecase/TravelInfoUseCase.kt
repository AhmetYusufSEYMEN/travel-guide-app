package com.seymen.seymentravel.domain.usecase

import com.seymen.seymentravel.domain.model.GuideModelItem
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

    suspend fun getAllInfo(): Flow<Resource<List<TravelModelItem>>> = flow {
        try {
            val data = iTravelInfoRepository.getTravelInfo()

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    suspend fun getCategoryAllInfo(): Flow<Resource<List<TravelModelItem>>> = flow {
        try {
            val data = iTravelInfoRepository.getCategoryAllInfo()

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    suspend fun getCategoryFlightInfo(): Flow<Resource<List<TravelModelItem>>> = flow {
        try {
            val data = iTravelInfoRepository.getCategoryFlightInfo()

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    suspend fun getCategoryHoteltInfo(): Flow<Resource<List<TravelModelItem>>> = flow {
        try {
            val data = iTravelInfoRepository.getCategoryHoteltInfo()

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    suspend fun getCategoryTransportationInfo(): Flow<Resource<List<TravelModelItem>>> = flow {
        try {
            val data = iTravelInfoRepository.getCategoryTransportationInfo()

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    suspend fun getTopDestinationInfo(): Flow<Resource<List<TravelModelItem>>> = flow {
        try {
            val data = iTravelInfoRepository.getTopDestinationInfo()

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    suspend fun getNearbyInfo(): Flow<Resource<List<TravelModelItem>>> = flow {
        try {
            val data = iTravelInfoRepository.getNearbyInfo()

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    suspend fun getMightNeedInfo(): Flow<Resource<List<TravelModelItem>>> = flow {
        try {
            val data = iTravelInfoRepository.getMightNeedInfo()

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    suspend fun getTopPickInfo(): Flow<Resource<List<TravelModelItem>>> = flow {
        try {
            val data = iTravelInfoRepository.getTopPickInfo()

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    suspend fun getBookmarkTrueInfo(): Flow<Resource<List<TravelModelItem>>> = flow {
        try {
            val data = iTravelInfoRepository.getBookmarkTrueInfo()

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    suspend fun getTripTrueInfo(): Flow<Resource<List<TravelModelItem>>> = flow {
        try {
            val data = iTravelInfoRepository.getTripTrueInfo()

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }


    suspend fun getInfoDetailsById(detailId: String): Flow<Resource<TravelModelItem>> = flow {
        try {
            val data = iTravelInfoRepository.getTravelInfoDetailsById(detailId)
            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    suspend fun updateWhatYouWant(updateWhatYouWant: TravelModelItem): Flow<Resource<TravelModelItem>> =
        flow {
            try {
                val data =
                    iTravelInfoRepository.updateTravelInfo(updateWhatYouWant, updateWhatYouWant.id)

                // loading
                emit(Resource.Loading())
                emit(Resource.Success(data))

            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage))
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage))
            }
        }

    suspend fun getGuideInfo(): Flow<Resource<List<GuideModelItem>>> = flow {
        try {
            val data = iTravelInfoRepository.getGuideInfo()

            // loading
            emit(Resource.Loading())
            emit(Resource.Success(data))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }


}
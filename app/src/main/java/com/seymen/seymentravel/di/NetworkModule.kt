package com.seymen.seymentravel.di

import com.seymen.seymentravel.data.remote.ApiService
import com.seymen.seymentravel.data.remote.repository.ITravelInfoRepositoryImpl
import com.seymen.seymentravel.domain.repository.ITravelInfoRepository
import com.seymen.seymentravel.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun providerGsonConverterFactory() : GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTravelInfoRepository(apiService: ApiService) : ITravelInfoRepository {
        return ITravelInfoRepositoryImpl(apiService)
    }


    /*
    @Module
    @InstallIn(SingletonComponent::class)
    object NetworkingModule {

    @Provides
    fun provideBaseURL():String {
        return "https://5e510330f2c0d300147c034c.mockapi.io/"
    }

    @Provides
    fun provideLogginInterceptor( ): HttpLoggingInterceptor {
        return  HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40,TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40,TimeUnit.SECONDS)
        okHttpClient.readTimeout(40,TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)

        return okHttpClient.build()
    }

    @Provides
    fun provideConverterFactory() : Converter.Factory {
        return  GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofit (okHttpClient: OkHttpClient,baseUrl: String,converterFactory: Converter.Factory ):Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    }

    @Provides
    fun provideRestApiService(retrofit: Retrofit): ApiService {
        return  retrofit.create(ApiService::class.java)
    }
}
   */

}
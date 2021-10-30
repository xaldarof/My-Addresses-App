package com.example.myaddressesapp.data.cloud.di

import com.example.myaddressesapp.data.cloud.AddressService
import com.example.myaddressesapp.data.cloud.CloudConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CloudModule {


    @Provides
    @Singleton
    fun provideAddressService(retrofit: Retrofit) = retrofit.create(AddressService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CloudConstants.BASE_URL)
            .build()
    }

}
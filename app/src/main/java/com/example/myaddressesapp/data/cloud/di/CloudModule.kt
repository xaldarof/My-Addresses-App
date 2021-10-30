package com.example.myaddressesapp.data.cloud.di

import com.example.myaddressesapp.data.cloud.AddressService
import com.example.myaddressesapp.data.cloud.CloudConstants
import com.example.myaddressesapp.data.cloud.GeoCodeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CloudModule {

    @Provides
    @Singleton
    fun provideAddressService(@CreateMapLocationRetrofit retrofit: Retrofit): AddressService =
        retrofit.create(AddressService::class.java)


    @Provides
    @Singleton
    fun provideGeoCoderService(@GoogleMapRetrofit retrofit: Retrofit): GeoCodeService =
        retrofit.create(GeoCodeService::class.java)

    @Singleton
    @Provides
    fun provideHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor {
        return Interceptor {
            val request = it.request().newBuilder()
            request.addHeader(name = CloudConstants.AUTHORIZATION, "Bearer ${CloudConstants.TOKEN}")
            it.proceed(request = request.build())
        }
    }

    @Provides
    @Singleton
    @CreateMapLocationRetrofit
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CloudConstants.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    @GoogleMapRetrofit
    fun provideMapRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CloudConstants.MAP_BASE_URL)
            .build()
    }
}
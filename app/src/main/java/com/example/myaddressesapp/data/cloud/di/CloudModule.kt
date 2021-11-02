package com.example.myaddressesapp.data.cloud.di

import android.content.Context
import com.example.myaddressesapp.data.cloud.service.AddressService
import com.example.myaddressesapp.data.cloud.CloudConstants
import com.example.myaddressesapp.data.cloud.service.GeoCodeService
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideGeoCoderService(@CustomGeoCoder retrofit: Retrofit): GeoCodeService =
        retrofit.create(GeoCodeService::class.java)


    @Singleton
    @Provides
    @CreateMapLocationClient
    fun provideHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    @CustomGeoCoderClient
    fun provideGeoCoderHttpClient(@ApplicationContext context: Context) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(context))
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

    fun provideRetrofit(@CreateMapLocationClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CloudConstants.BASE_URL)
            .build()
    }



    @Provides
    @Singleton
    @CustomGeoCoder
    fun provideGeoCoderRetrofit(@CustomGeoCoderClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CloudConstants.GEO_CODER_BASE_URL)
            .build()
    }
}
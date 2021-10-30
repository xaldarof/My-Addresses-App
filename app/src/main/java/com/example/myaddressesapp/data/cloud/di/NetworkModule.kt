package com.example.myaddressesapp.data.cloud.di//package com.example.myaddressesapp.data.cloud.di
//
//import android.content.Context
//import com.chuckerteam.chucker.api.ChuckerInterceptor
//import com.mocklets.pluto.PlutoInterceptor
//import com.squareup.moshi.Moshi
//import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import io.socket.client.IO
//import io.socket.client.Socket
//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.create
//import uz.unical.network.BuildConfig
//import uz.unical.network.api.address.AddressService
//import uz.unical.network.api.auth.AuthService
//import uz.unical.network.api.bonuses.BonusService
//import uz.unical.network.api.card.CardService
//import uz.unical.network.api.geocoder.GeocoderService
//import uz.unical.network.api.oil.OilService
//import uz.unical.network.api.order.OrderService
//import uz.unical.network.api.profile.ProfileService
//import uz.unical.network.api.settings.SettingsService
//import uz.unical.network.api.upload.UploadService
//import uz.unical.network.util.*
//import uz.unical.network.util.Constants.NETWORK_TIMEOUT
//import java.util.concurrent.TimeUnit
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkModule {
//
//    @Provides
//    @Singleton
//    @Default
//    fun retrofit(
//        @Default okHttpClient: OkHttpClient,
//        moshi: Moshi
//    ): Retrofit {
//        return Retrofit
//            .Builder()
//            .baseUrl(BuildConfig.BASE_URL)
//            .client(okHttpClient)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    @YandexGeocoder
//    fun yandexGeocoderRetrofit(
//        @Default okHttpClient: OkHttpClient,
//        moshi: Moshi
//    ): Retrofit {
//        return Retrofit
//            .Builder()
//            .baseUrl(BuildConfig.BASE_URL_YANDEX_GEOCODER)
//            .client(okHttpClient)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    @Upload
//    fun uploadRetrofit(
//        @Upload okHttpClient: OkHttpClient,
//        moshi: Moshi
//    ): Retrofit {
//        return Retrofit
//            .Builder()
//            .baseUrl(BuildConfig.BASE_URL_UPLOAD)
//            .client(okHttpClient)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    @Default
//    fun okhttp(
//        @Default appInterceptor: Interceptor,
//        @Chucker chuckerInterceptor: ChuckerInterceptor
//    ): OkHttpClient {
//        return OkHttpClient
//            .Builder()
//            .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
//            .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
//            .addInterceptor(appInterceptor)
//            .addInterceptor(chuckerInterceptor)
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    @Upload
//    fun uploadOkhttp(
//        @Default appInterceptor: Interceptor,
//        @Chucker chuckerInterceptor: ChuckerInterceptor
//    ): OkHttpClient {
//        return OkHttpClient
//            .Builder()
//            .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
//            .readTimeout(1800000, TimeUnit.SECONDS)
//            .addInterceptor(appInterceptor)
//            .addInterceptor(chuckerInterceptor)
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun moshi(): Moshi {
//        return Moshi
//            .Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
//    }
//
//    @Default
//    @Provides
//    @Singleton
//    fun interceptor(): Interceptor {
//        return Interceptor {
//            val request = it.request().newBuilder()
//            request.addHeader(
//                BuildConfig.AUTHORIZATION,
//                "Bearer ${Constants.TOKEN}"
//            )
//            it.proceed(request.build())
//        }
//    }
//
//    @Singleton
//    @Provides
//    fun socketIo(@Default okHttpClient: OkHttpClient): Socket {
//        val options = IO.Options()
//        options.forceNew = true
//        options.reconnection = true
//        options.reconnectionDelay = 100
//        options.callFactory = okHttpClient
//        options.path = "/socket.io"
//        return IO.socket(BuildConfig.BASE_URL_SOCKET, options)
//    }
//
//    @Pluto
//    @Provides
//    @Singleton
//    fun pluto(): PlutoInterceptor = PlutoInterceptor()
//
//    @Chucker
//    @Provides
//    @Singleton
//    fun chucker(@ApplicationContext context: Context): ChuckerInterceptor =
//        ChuckerInterceptor(context = context)
//
//
//    //auth
//    @Provides
//    @Singleton
//    fun authService(@Default retrofit: Retrofit): AuthService = retrofit.create()
//
//    //address
//    @Provides
//    @Singleton
//    fun addressService(@Default retrofit: Retrofit): AddressService = retrofit.create()
//
//    //geocoder
//    @Provides
//    @Singleton
//    fun geocoderService(@YandexGeocoder retrofit: Retrofit): GeocoderService = retrofit.create()
//
//
//    //settings
//    @Provides
//    @Singleton
//    fun settingsService(@Default retrofit: Retrofit): SettingsService = retrofit.create()
//
//    //oil
//    @Provides
//    @Singleton
//    fun oilService(@Default retrofit: Retrofit): OilService = retrofit.create()
//
//    //upload
//    @Provides
//    @Singleton
//    fun uploadService(@Upload retrofit: Retrofit): UploadService = retrofit.create()
//
//    //profile
//    @Provides
//    @Singleton
//    fun profileService(@Default retrofit: Retrofit): ProfileService = retrofit.create()
//
//    //order
//    @Provides
//    @Singleton
//    fun orderService(@Default retrofit: Retrofit): OrderService = retrofit.create()
//
//    //card
//    @Provides
//    @Singleton
//    fun cardService(@Default retrofit: Retrofit): CardService = retrofit.create()
//
//    //bonus
//    @Provides
//    @Singleton
//    fun getBonuses(@Default retrofit: Retrofit): BonusService = retrofit.create()
//
//}
package com.example.myaddressesapp.data.cloud.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CustomGeoCoder

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CreateMapLocationRetrofit


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CreateMapLocationClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CustomGeoCoderClient
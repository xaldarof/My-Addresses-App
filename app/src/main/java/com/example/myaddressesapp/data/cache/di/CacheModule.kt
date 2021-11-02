package com.example.myaddressesapp.data.cache.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.myaddressesapp.data.cache.AddressCacheRepository
import com.example.myaddressesapp.data.cache.AppDatabase
import com.example.myaddressesapp.data.cache.CacheConstants
import com.example.myaddressesapp.data.cache.UserLastLocationDataSource
import com.example.myaddressesapp.data.cache.dao.AddressDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideAddressDao(appDatabase: AppDatabase): AddressDao {
        return appDatabase.provideAddressesDao()
    }


    @Provides
    fun provideCacheRepository(dao: AddressDao,
        userLastLocationDataSource: UserLastLocationDataSource.Base): AddressCacheRepository {

        return AddressCacheRepository.Base(dao, userLastLocationDataSource)
    }

    @Provides
    fun provideUserLocationDataSource(sharedPreferences: SharedPreferences): UserLastLocationDataSource {
        return UserLastLocationDataSource.Base(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context):SharedPreferences {
        return context.getSharedPreferences(CacheConstants.PREFERENCES_NAME,MODE_PRIVATE)
    }
}
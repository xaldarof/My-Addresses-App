package com.example.myaddressesapp.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myaddressesapp.data.cache.dao.AddressDao
import com.example.myaddressesapp.data.cache.models.AddressModelDb


@Database(entities = [AddressModelDb::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun provideAddressesDao(): AddressDao

    companion object {

        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, CacheConstants.DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}
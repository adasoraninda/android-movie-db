package com.adasoraninda.mymoviedb.di.module

import android.content.Context
import androidx.room.Room
import com.adasoraninda.mymoviedb.data.local.database.MovieDatabase
import com.adasoraninda.mymoviedb.data.local.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }

}
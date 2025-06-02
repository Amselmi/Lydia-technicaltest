package com.users.di

import android.content.Context
import androidx.room.Room
import com.users.data.Endpoints.BASE_URL
import com.users.data.db.UsersDatabase
import com.users.data.api.UsersApi
import com.users.data.repository.Repository
import com.users.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providerRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providerApiService(retrofit: Retrofit) : UsersApi {
        return retrofit.create(UsersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersDatabase(@ApplicationContext context: Context): UsersDatabase {
        return Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            "users.db"
        ).build()
    }
    @Provides
    @Singleton
    fun providerRepositoryImpl(apiService: UsersApi,db:UsersDatabase) : Repository {
        return RepositoryImpl(apiService,db)
    }

}
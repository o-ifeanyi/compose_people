package com.example.people.di

import com.example.people.data.PeopleRepositoryImpl
import com.example.people.domain.PeopleRepository
import com.example.people.service.PeopleApi
import com.example.people.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providePeopleApi(): PeopleApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PeopleApi::class.java)
    }

    @Provides
    @Singleton
    fun providePeopleRepository(api: PeopleApi): PeopleRepository = PeopleRepositoryImpl(api = api)
}
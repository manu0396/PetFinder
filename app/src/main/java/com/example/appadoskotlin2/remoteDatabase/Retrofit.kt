package com.example.appadoskotlin2.remoteDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object Retrofit {
    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(RemoteAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    @Provides
    @Singleton
    fun provideServiceAPI(retrofit: Retrofit): RemoteAPI =
        retrofit.create(RemoteAPI::class.java)
}
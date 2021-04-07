package com.petter.testapplication.presentation.di

import com.jakewharton.espresso.OkHttp3IdlingResource
import com.petter.testapplication.service.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val client = OkHttpClient.Builder().apply {
    addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
}.build()

val idleResource = OkHttp3IdlingResource.create("okhttp", client)

@Module
@InstallIn(FragmentComponent::class)
class PlaylistModule {


    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl("http://192.168.1.43:3000/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun playlistApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}
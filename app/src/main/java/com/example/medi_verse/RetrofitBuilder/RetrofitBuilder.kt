package com.example.medi_verse.RetrofitBuilder

import com.example.medi_verse.data.remote.ApiService
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.repository.RemoteRepoImpl
import com.example.medi_verse.utils.SessionManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun provideMoshi(): Moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val httpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

val client = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .build()

fun provideApiService(moshi: Moshi) : ApiService = Retrofit
    .Builder()
    .run {
        baseUrl(ApiService.BASE_URL)
        client(client)
        addConverterFactory(MoshiConverterFactory.create(moshi))
        build()
    }.create(ApiService::class.java)


fun provideRemoteRepo(
    apiService: ApiService,
    sessionManager: SessionManager
): RemoteRepo{
    return RemoteRepoImpl(
        apiService,
        sessionManager
    )
}
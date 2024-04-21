package com.example.medi_verse.data.remote

import com.example.medi_verse.data.remote.model.AuthResponse
import com.example.medi_verse.data.remote.model.LoginRequest
import com.example.medi_verse.data.remote.model.RegisterRequest
import com.example.utils.Constants.LOGIN_END_POINT
import com.example.utils.Constants.REGISTER_END_POINT
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    companion object{
        const val BASE_URL = "https://aaryaworks.tech/"
    }

    @Headers("Content-Type: application/json")
    @POST(REGISTER_END_POINT)
    suspend fun createUserAccount(
        @Body user:RegisterRequest
    ): AuthResponse

    @Headers("Content-Type: application/json")
    @POST(LOGIN_END_POINT)
    suspend fun loginUser(
        @Body user:LoginRequest
    ): AuthResponse



}
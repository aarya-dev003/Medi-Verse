package com.example.medi_verse.repository

import com.example.medi_verse.data.remote.model.LoginRequest
import com.example.medi_verse.data.remote.model.RegisterRequest
import com.example.medi_verse.utils.Result

interface RemoteRepo {
    suspend fun createUser(user : RegisterRequest): Result<String>
    suspend fun loginUser(user : LoginRequest): Result<String>
    suspend fun getUser(): Result<RegisterRequest>
    suspend fun logout(): Result<String>

}
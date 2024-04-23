package com.example.medi_verse.repository

import com.example.medi_verse.data.remote.model.LoginRequest
import com.example.medi_verse.data.remote.model.Post
import com.example.medi_verse.data.remote.model.RegisterRequest
import com.example.medi_verse.utils.Result
import com.example.requests.ClubLoginRequest

interface RemoteRepo {

    //for user
    suspend fun createUser(user : RegisterRequest): Result<String>
    suspend fun loginUser(user : LoginRequest): Result<String>
    suspend fun getUser(): Result<RegisterRequest>
    suspend fun logout(): Result<String>

    //for club

    suspend fun loginClub(club : ClubLoginRequest): Result<String>


    //for collegeAdmin
    suspend fun loginAdmin(admin: LoginRequest): Result<String>


    //posts
    suspend fun createPost(post : Post) : Result<String>

    suspend fun retrievePostClub() : Result<String>

    suspend fun retrievePostUser() : Result<String>

}
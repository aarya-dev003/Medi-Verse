package com.example.medi_verse.repository

import com.example.medi_verse.data.remote.ApiService
import com.example.medi_verse.data.remote.model.LoginRequest
import com.example.medi_verse.data.remote.model.Post
import com.example.medi_verse.data.remote.model.RegisterRequest
import com.example.medi_verse.utils.Result
import com.example.medi_verse.utils.Result.Error
import com.example.medi_verse.utils.Result.Success
import com.example.medi_verse.utils.SessionManager
import com.example.medi_verse.utils.isNetworkConnected
import com.example.requests.ClubLoginRequest

class RemoteRepoImpl (
    private val apiService: ApiService,
    private val sessionManager: SessionManager
): RemoteRepo {
    override suspend fun createUser(user: RegisterRequest): Result<String> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Error("No Internet Connection!", "")
            } else {
                val result = apiService.createUserAccount(user)
                sessionManager.createLoginContext("user")
                result.let {
                    sessionManager.updateSession(
                        token = it.token ?: "",
                        name = user.name,
                        email = user.email
                    )
                    Success("User Created Successfully!")
                } ?: Error("Some Error Occurred", "")
            }
        } catch (e : Exception) {
            Error(e.message ?: "Some Problem Occurred!", "")
        }
    }

    override suspend fun loginUser(user: LoginRequest): Result<String> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Error("No Internet Connection!", "")
            } else {
                val result = apiService.loginUser(user)
                val name = sessionManager.getCurrentUsername()
                sessionManager.createLoginContext("user")
                result.let {
                    name?.let { it1 ->
                        sessionManager.updateSession(
                            token = it.token ?: "",
                            name = it1,
                            email = user.email
                        )
                    }
                    Success("User Created Successfully!")
                } ?: Error("Some Error Occurred", "")
            }
        } catch (e : Exception) {
            Error(e.message ?: "Some Problem Occurred!", "")
        }
    }
    override suspend fun getUser(): Result<RegisterRequest> {
        return try {
            val context = sessionManager.getLoginContext()
            val name = sessionManager.getCurrentUsername()
            val email = sessionManager.getCurrentEmail()
            if (name == null || email == null) {
                Result.Error("User not Logged In!" ,RegisterRequest(name = "", email = "", username = "", password = "") )
            } else {
                Result.Success(RegisterRequest(name = name, email = email, username = "", password = ""))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Some Problem Occurred", RegisterRequest(name = "", email = "", username = "", password = ""))
        }
    }


    override suspend fun logout(): Result<String> {
        return try {
            sessionManager.logout()
            Result.Success("Logged Out Successfully!")
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Some Problem Occurred"," ")
        }
    }


    //for club
    override suspend fun loginClub(club: ClubLoginRequest): Result<String> {
        return try{
            if (!isNetworkConnected(sessionManager.context)) {
                Error("No Internet Connection!", "")
            } else {
                val result = apiService.loginClub(club)
                val name = sessionManager.getCurrentUsername()
                sessionManager.createLoginContext("club")
                result.let {
                    name?.let { it1 ->
                        sessionManager.updateSession(
                            token = it.token ?: "",
                            name = it1,
                            email = club.username
                        )
                    }
                    Success("User Created Successfully!")
                } ?: Error("Some Error Occurred", "")
            }
        } catch (e : Exception) {
            Error(e.message ?: "Some Problem Occurred!", "")
        }
    }

    override suspend fun loginAdmin(admin: LoginRequest): Result<String> {
        return try{
            if (!isNetworkConnected(sessionManager.context)) {
                Error("No Internet Connection!", "")
            } else {
                val result = apiService.loginAdmin(admin)
                val name = sessionManager.getCurrentUsername()
                sessionManager.createLoginContext("admin")
                result.let {
                    name?.let { it1 ->
                        sessionManager.updateSession(
                            token = it.token ?: "",
                            name = it1,
                            email = admin.email
                        )
                    }
                    Success("Admin Login Successfully!!")
                } ?: Error("Some Error Occurred", "")
            }
        } catch (e : Exception) {
            Error(e.message ?: "Some Problem Occurred!", "")
        }
    }

    override suspend fun createPost(post: Post): Result<String> {
        try {
            val token = sessionManager.getJwtToken()
                ?: return Result.Success("Post will be saved in Local Database")

            val result = apiService.createPost("Bearer $token", post)

            return Result.Success(result)
        } catch (e: Exception) {
            return Result.Error("An error occurred while creating post","")
        }
    }

    override suspend fun retrievePostClub(): Result<String> {
        try{
            val token = sessionManager.getJwtToken()
                ?: return Result.Error("jwt token does not exits", "")

            val result = apiService.retrievePost("Bearer $token")

            if(result != null){
                return Result.Success("$result", "Post retrieved successfully")

            }else{
                return  Result.Error("Error Retrieving post",result)
            }

        }catch (e:Exception){
            return Result.Error("An error occurred while recieving post","")
        }
    }

   override suspend fun retrievePostUser(): Result<String>{
        try {
            val token = sessionManager.getJwtToken()
                ?: return Result.Error("No token Exits", "")

            val result = apiService.retrievePostUser("Bearer $token")

            if (result != null){
                return Result.Success("$result", "Post recieved Successfully")
            }else{
                return Result.Error("An error Occurred while retrieving", "$result")
            }
        }catch (e: Exception){
            return Result.Error("Cannot Retrieve", "")
        }
    }


}

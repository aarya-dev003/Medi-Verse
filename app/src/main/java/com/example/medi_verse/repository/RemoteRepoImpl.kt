package com.example.medi_verse.repository

import com.example.medi_verse.data.remote.ApiService
import com.example.medi_verse.data.remote.model.LoginRequest
import com.example.medi_verse.data.remote.model.RegisterRequest
import com.example.medi_verse.utils.Result
import com.example.medi_verse.utils.Result.Error
import com.example.medi_verse.utils.Result.Success
import com.example.medi_verse.utils.SessionManager
import com.example.medi_verse.utils.isNetworkConnected

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


}

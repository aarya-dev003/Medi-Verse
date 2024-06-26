package com.example.medi_verse.repository

import android.util.Log
import com.example.medi_verse.data.remote.ApiService
import com.example.medi_verse.data.remote.model.Announcement
import com.example.medi_verse.data.remote.model.ClubDto
import com.example.medi_verse.data.remote.model.ClubRegisterRequest
import com.example.medi_verse.data.remote.model.FeedbackItem
import com.example.medi_verse.data.remote.model.FeedbackRequest
import com.example.medi_verse.data.remote.model.GetPost
import com.example.medi_verse.data.remote.model.LoginRequest
import com.example.medi_verse.data.remote.model.Post
import com.example.medi_verse.data.remote.model.RegisterRequest
import com.example.medi_verse.data.remote.model.SearchPost
import com.example.medi_verse.utils.Result
import com.example.medi_verse.utils.Result.Error
import com.example.medi_verse.utils.Result.Success
import com.example.medi_verse.utils.SessionManager
import com.example.medi_verse.utils.isNetworkConnected
import com.example.requests.ClubLoginRequest
import retrofit2.HttpException

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
                if (result.token != null) {
                    sessionManager.updateSession(
                        token = result.token.toString(),
                        name = user.name,
                        email = user.email
                    )
                    Result.Success(result.token)
                }  else {
                    Result.Error("Some Error Occurred!", "")
                }

            }
        } catch (e : Exception) {
            Error(e.message ?: "Some Problem Occurred!", "")
        }
    }

    override suspend fun loginUser(user: LoginRequest): Result<String> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Result.Error("No Internet Connection!", "")
            } else {
                val result = apiService.loginUser(user)
                val name = sessionManager.getCurrentUsername()
                sessionManager.createLoginContext("user")
                if (result.token != null) {
                    sessionManager.updateSession(
                        token = result.token.toString(),
                        name = name ?: "student",
                        email = user.email
                    )
                    Result.Success(result.token)
                } else {
                    Result.Error("Some Error Occurred!", "")
                }
            }
        } catch (e: HttpException) {
            if (e.code() == 409) {
                Result.Error("Invalid Credentials", "")
            } else {
                Result.Error(e.message ?: "Some Problem Occurred!", "")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Some Problem Occurred!", "")
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
                if (result.token != null) {
                    sessionManager.updateSession(
                        token = result.token.toString(),
                        name = name?:club.username,
                        email = club.username
                    )
                    Result.Success(result.token)
                } else {
                    Result.Error("Some Error Occurred!", "")
                }
            }
        } catch (e : Exception) {
            Error(e.message ?: "Some Problem Occurred!", "")
        }
    }

    override suspend fun loginAdmin(admin: LoginRequest): Result<String> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Result.Error("No Internet Connection!","")
            } else {
                val result = apiService.loginAdmin(admin)
                val name = sessionManager.getCurrentUsername()
                sessionManager.createLoginContext("admin")
                if (result.token != null ) {
                    sessionManager.updateSession(
                        token = result.token.toString(),
                        name = name?: "admin",
                        email = admin.email
                    )
                    Result.Success(result.token)
                } else {
                    Result.Error("Some Error Occurred!", "")
                }
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Some Problem Occurred!","")
        }
    }


    override suspend fun createPost(post: Post): Result<String> {
        try {
            val token = sessionManager.getJwtToken()

            if(token == null){
                Result.Error("JWT Token Not Exits", "")
            }

            Log.d("token", "$token")

            val result = apiService.createPost(
                "Bearer $token",
                post
            )

            return Result.Success(result)
        } catch (e: Exception) {
            return Result.Error("An error occurred while creating post","")
        }
    }

    override suspend fun retrievePostClub(): Result<List<GetPost>> {
        try {
            val token = sessionManager.getJwtToken()
                ?: return Result.Error("No token Exists", emptyList())

            val result = apiService.retrievePost("Bearer $token")

            if (result.isNotEmpty()) {
                return Result.Success(result, "Posts received Successfully")
            } else {
                return Result.Error("An error Occurred while retrieving", emptyList())
            }
        } catch (e: Exception) {
            return Result.Error("Cannot Retrieve", emptyList())
        }
    }

    override suspend fun retrievePostUser(): Result<List<GetPost>> {
        try {
            val token = sessionManager.getJwtToken()
                ?: return Result.Error("No token Exists", emptyList())

            val result = apiService.retrievePostUser("Bearer $token")

            if (result.isNotEmpty()) {
                return Result.Success(result, "Posts received Successfully")
            } else {
                return Result.Error("An error Occurred while retrieving", emptyList())
            }
        } catch (e: Exception) {
            return Result.Error("Cannot Retrieve", emptyList())
        }
    }



    //tasks for college admin
    override suspend fun createAnnouncement(announcement: Announcement): Result<String> {
        try{
            val token = sessionManager.getJwtToken()
            if(token == null){
                Result.Error("JWT Token Not Exits", "")
            }


            val result = apiService.createAnnouncement(
                "Bearer $token",
                announcement
            )
            return Result.Success(result)
        } catch (e: Exception) {
            return Result.Error("An error occurred while creating post","")
        }
    }

    override suspend fun createClubAdmin(club: ClubRegisterRequest): Result<String> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Result.Error("No Internet Connection!","")
            } else {
                val token = sessionManager.getJwtToken()
                val result = apiService.createClub(
                    "Bearer $token",
                    club
                )
                if (result.token != null) {
                    Result.Success("$result", "Club Created Successfully")
                } else {
                    Result.Error("Some Error Occurred","")
                }
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Some Problem Occurred!", "")
        }
    }

    override suspend fun getAnnouncementUser(): Result<List<Announcement>> {
        try {
            val token = sessionManager.getJwtToken() ?: return Result.Error("jwt token does not exist", emptyList())

            val result = apiService.getAnnouncementUser("Bearer $token")

            return if (result.isNotEmpty()) {
                Result.Success(result, "Announcements retrieved successfully")
            } else {
                Result.Error("No announcements found", result)
            }
        } catch (e: Exception) {
            return Result.Error("$e.message ?: An error occurred while receiving announcements", emptyList())
        }
    }

    override suspend fun createFeedback(feedback : FeedbackRequest): Result<String> {
        try{
            val token = sessionManager.getJwtToken()
            if(token == null){
                Result.Error("JWT Token Not Exits", "")
            }

            val result = apiService.createFeedback(
                "Bearer $token",
                feedback
            )
            return Result.Success(result)
        } catch (e: Exception) {
            return Result.Error("An error occurred while creating feedback","")
        }
    }

    override suspend fun getFeedbackClub(): Result<List<FeedbackItem>> {
        try {
            val token = sessionManager.getJwtToken() ?: return Result.Error("jwt token does not exist", emptyList())

            val result = apiService.getFeedbackClub("Bearer $token")

            return if (result.isNotEmpty()) {
                Result.Success(result, "Feedback retrieved successfully")
            } else {
                Result.Error("No Feedbacks found", result)
            }
        } catch (e: Exception) {
            return Result.Error("$e.message ?: An error occurred while receiving Feedbacks", emptyList())
        }
    }

    override suspend fun getFeedbackAdmin(): Result<List<FeedbackItem>> {
        try {
            val token = sessionManager.getJwtToken() ?: return Result.Error("jwt token does not exist", emptyList())

            val result = apiService.getFeedbackAdmin("Bearer $token")

            return if (result.isNotEmpty()) {
                Result.Success(result, "Feedback retrieved successfully")
            } else {
                Result.Error("No Feedbacks found", result)
            }
        } catch (e: Exception) {
            return Result.Error("$e.message ?: An error occurred while receiving Feedbacks", emptyList())
        }
    }

    override suspend fun searchPostClub(search : SearchPost): Result<List<GetPost>> {
        try {
            val token = sessionManager.getJwtToken()
                ?: return Result.Error("No token Exists", emptyList())

            val result = apiService.searchPostClub("Bearer $token", search)

            if (result.isNotEmpty()) {
                return Result.Success(result, "Posts received Successfully")
            } else {
                return Result.Error("An error Occurred while retrieving", emptyList())
            }
        } catch (e: Exception) {
            return Result.Error("Cannot Retrieve", emptyList())
        }
    }

    override suspend fun searchPostUser(search : SearchPost): Result<List<GetPost>> {
        try {
            val token = sessionManager.getJwtToken()
                ?: return Result.Error("No token Exists", emptyList())

            val result = apiService.searchPostUser("Bearer $token", search)

            if (result.isNotEmpty()) {
                return Result.Success(result, "Posts received Successfully")
            } else {
                return Result.Error("An error Occurred while retrieving", emptyList())
            }
        } catch (e: Exception) {
            return Result.Error("Cannot Retrieve", emptyList())
        }
    }

    override suspend fun getClubData(): Result<ClubDto> {
        try {
            val token = sessionManager.getJwtToken() ?:
                return Result.Error("jwt token does not exist", null)

            val result = apiService.getClubData("Bearer $token")

            return if (result != null ) {
                Result.Success(result, "")
            } else {
                Result.Error("Cannot Retrieve Club Data", result)
            }
        } catch (e: Exception) {
            return Result.Error("$e.message ?: An error occurred", null)
        }
    }
}

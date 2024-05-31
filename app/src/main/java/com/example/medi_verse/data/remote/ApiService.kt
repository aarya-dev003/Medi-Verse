package com.example.medi_verse.data.remote

import com.example.medi_verse.data.remote.model.Announcement
import com.example.medi_verse.data.remote.model.AuthResponse
import com.example.medi_verse.data.remote.model.FeedbackItem
import com.example.medi_verse.data.remote.model.FeedbackRequest
import com.example.medi_verse.data.remote.model.GetPost
import com.example.medi_verse.data.remote.model.LoginRequest
import com.example.medi_verse.data.remote.model.Post
import com.example.medi_verse.data.remote.model.RegisterRequest
import com.example.requests.ClubLoginRequest
import com.example.utils.Constants.ADMIN_LOGIN
import com.example.utils.Constants.CREATE_ANNOUNCEMENT
import com.example.utils.Constants.CREATE_CLUB
import com.example.utils.Constants.CREATE_END_POINT
import com.example.utils.Constants.CREATE_FEEDBACK
import com.example.utils.Constants.GET_ANNOUNCEMENT_USER
import com.example.utils.Constants.GET_FEEDBACK_ADMIN
import com.example.utils.Constants.GET_FEEDBACK_CLUB
import com.example.utils.Constants.LOGIN_CLUB_ADMIN
import com.example.utils.Constants.LOGIN_END_POINT
import com.example.utils.Constants.REGISTER_END_POINT
import com.example.utils.Constants.RETRIEVE_END_POINT
import com.example.utils.Constants.RETRIEVE_END_POINT_USER
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    companion object{
        //const val BASE_URL = "https://social-media-server-mva3.onrender.com"
        const val BASE_URL = "https://aaryaworks.tech/"
    }

    //for users (student)
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

    //For Club
//    @Headers("Content-Type: application/json")
//    @POST(REGISTER_END_POINT)
//    suspend fun createUserAccount(
//        @Body user:RegisterRequest
//    ): AuthResponse

    @Headers("Content-Type: application/json")
    @POST(LOGIN_CLUB_ADMIN)
    suspend fun loginClub(
        @Body club: ClubLoginRequest
    ): AuthResponse


    //for college admin
  @Headers("Content-Type: application/json")
    @POST(ADMIN_LOGIN)
    suspend fun loginAdmin(
        @Body admin:LoginRequest
    ): AuthResponse


    //Post
    @Headers("Content-Type: application/json")
    @POST(CREATE_END_POINT)
    suspend fun createPost(
        @Header("Authorization") token:String,
        @Body post: Post
    ): String

    @Headers("Content-Type: application/json")
    @GET(RETRIEVE_END_POINT)
    suspend fun retrievePost(
        @Header("Authorization") token:String
    ): List<GetPost>


    @Headers("Content-Type: application/json")
    @GET(RETRIEVE_END_POINT_USER)
    suspend fun retrievePostUser(
        @Header("Authorization") token:String
    ): List<GetPost>

    @Headers("Content-Type: application/json")
    @POST(CREATE_ANNOUNCEMENT)
    suspend fun createAnnouncement(
        @Header("Authorization") token: String,
        @Body announcement : Announcement
    ): String

    @Headers("Content-Type: application/json")
    @POST(CREATE_CLUB)
    suspend fun createClub(
        @Header ("Authorization") token : String,
        @Body club : RegisterRequest
    ): AuthResponse


    @Headers("Content-Type: application/json")
    @GET(GET_ANNOUNCEMENT_USER)
    suspend fun getAnnouncementUser(
        @Header ("Authorization") token : String
    ): List<Announcement>

    @Headers("Content-Type: application/json")
    @POST(CREATE_FEEDBACK)
    suspend fun createFeedback(
        @Header ("Authorization") token: String,
        @Body feedback : FeedbackRequest
    ) : String


    @Headers("Content-Type: application/json")
    @GET(GET_FEEDBACK_CLUB)
    suspend fun getFeedbackClub(
        @Header ("Authorization") token : String
    ): List<FeedbackItem>

    @Headers("Content-Type: application/json")
    @GET(GET_FEEDBACK_ADMIN)
    suspend fun getFeedbackAdmin(
        @Header ("Authorization") token : String
    ): List<FeedbackItem>

}
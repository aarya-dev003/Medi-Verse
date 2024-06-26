package com.example.medi_verse.repository

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

    suspend fun createClubAdmin(club: ClubRegisterRequest): Result<String>

    //posts
    suspend fun createPost(post : Post) : Result<String>

    suspend fun retrievePostClub() : Result<List<GetPost>>

    suspend fun retrievePostUser() : Result<List<GetPost>>

    suspend fun createAnnouncement(announcement: Announcement) : Result<String>

    suspend fun getAnnouncementUser(): Result<List<Announcement>>

    suspend fun createFeedback(feedback : FeedbackRequest): Result<String>

    suspend fun getFeedbackClub(): Result<List<FeedbackItem>>
    suspend fun getFeedbackAdmin(): Result<List<FeedbackItem>>

    suspend fun searchPostClub(search : SearchPost) : Result<List<GetPost>>
    suspend fun searchPostUser(search : SearchPost) : Result<List<GetPost>>

    suspend fun getClubData(): Result<ClubDto>

}
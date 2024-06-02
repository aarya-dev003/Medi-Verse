package com.example.medi_verse.data.remote.model

data class ClubRegisterRequest(
    val username : String,
    val name : String,
    val email : String ,
    val password : String,
    val imageUrl : String?
)

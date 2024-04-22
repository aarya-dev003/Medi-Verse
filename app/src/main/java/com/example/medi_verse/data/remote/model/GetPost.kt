package com.example.medi_verse.data.remote.model

data class GetPost(
    val username: String,
    val image: String,
    val description: String,
    val time: Long,
    val id: String
)

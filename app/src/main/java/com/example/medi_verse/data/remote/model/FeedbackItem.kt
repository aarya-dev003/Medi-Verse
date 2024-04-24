package com.example.medi_verse.data.remote.model

data class FeedbackItem(
    val id: String,
    val issue: String,
    val issueType: String,
    val time: Long,
    val username: String
)
package com.example.medi_verse.data.remote.model

data class FeedbackRequest(
    val issue: String,
    val issueType: String,
    val time: Long
)
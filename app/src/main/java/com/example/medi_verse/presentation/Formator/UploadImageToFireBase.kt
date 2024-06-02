package com.example.medi_verse.presentation.Formator

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await

private const val TAG ="upload post"

suspend fun uploadImageToFirebase(context: Context, imageUri: Uri, description: String, folderRef : String): String? {
    val storageRef = Firebase.storage.reference
    val imagesRef = storageRef.child("$folderRef")

    val fileRef = imagesRef.child("image_${System.currentTimeMillis()}.jpg")
    val uploadTask = fileRef.putFile(imageUri).await()

    return try {
        val imageUrl = uploadTask.storage.downloadUrl.await().toString()
        val imageDescription = description
        Toast.makeText(context, "Photo posted successfully", Toast.LENGTH_SHORT).show()
        imageUrl
    } catch (e: Exception) {
        Log.e(TAG, "Error uploading image: $e")
        Toast.makeText(context, "Failed to post photo: ${e.message}", Toast.LENGTH_SHORT).show()
        null
    }
}
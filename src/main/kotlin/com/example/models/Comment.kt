package com.example.models

data class Comment(
    val id: String = "",  // Firebase document ID
    val postId: String,   // ID of the post this comment belongs to
    val authorId: String, // UID from Firebase Authentication
    val content: String,  // Content of the comment
    val timestamp: Long = System.currentTimeMillis()  // Time when the comment was created
)

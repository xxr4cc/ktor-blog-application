package com.example.models

data class Post(
    val id: String = "",  // Firebase document ID
    val title: String,
    val content: String,
    val authorId: String,  // UID from Firebase Authentication
    val timestamp: Long = System.currentTimeMillis()
)

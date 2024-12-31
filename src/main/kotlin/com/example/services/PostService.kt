package com.example.services

import com.example.models.Post
import com.google.firebase.firestore.FirebaseFirestore

class PostService {
    private val db = FirebaseFirestore.getInstance()

    // Create Post
    fun createPost(post: Post) {
        db.collection("posts")
            .add(post)
            .addOnSuccessListener {
                println("Post created successfully")
            }
            .addOnFailureListener { e ->
                println("Error creating post: $e")
            }
    }

    // Get All Posts
    fun getPosts(callback: (List<Post>) -> Unit) {
        db.collection("posts")
            .get()
            .addOnSuccessListener { result ->
                val posts = result.map { document ->
                    document.toObject(Post::class.java)
                }
                callback(posts)
            }
            .addOnFailureListener { e ->
                println("Error getting posts: $e")
            }
    }
}

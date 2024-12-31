package com.example.services

import com.example.models.Comment
import com.google.firebase.firestore.FirebaseFirestore

class CommentService {
    private val db = FirebaseFirestore.getInstance()

    // Add Comment to a Post
    fun addComment(comment: Comment) {
        db.collection("comments")
            .add(comment)
            .addOnSuccessListener {
                println("Comment added successfully")
            }
            .addOnFailureListener { e ->
                println("Error adding comment: $e")
            }
    }

    // Get Comments for a Post
    fun getComments(postId: String, callback: (List<Comment>) -> Unit) {
        db.collection("comments")
            .whereEqualTo("postId", postId)
            .get()
            .addOnSuccessListener { result ->
                val comments = result.map { document ->
                    document.toObject(Comment::class.java)
                }
                callback(comments)
            }
            .addOnFailureListener { e ->
                println("Error getting comments: $e")
            }
    }
}

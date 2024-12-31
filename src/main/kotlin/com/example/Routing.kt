import com.example.firebase.MyAuthenticatedUser
import com.example.models.Post
import com.example.models.Comment
import com.example.services.PostService
import com.example.services.CommentService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.auth.*
import io.ktor.http.*
import io.ktor.server.request.*
import kotlinx.coroutines.launch

fun Application.configureRouting() {
    val postService = PostService()
    val commentService = CommentService()

    routing {
        route("/posts") {
            // Create Post
            post {
                authenticate {
                    val user = call.principal<MyAuthenticatedUser>()
                    if (user != null) {
                        val post = call.receive<Post>()
                        postService.createPost(post.copy(authorId = user.id))  // Include authorId
                        call.respond(HttpStatusCode.Created, "Post created successfully")
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "User is not authenticated")
                    }
                }
            }

            // Get All Posts
            get {
                launch {
                    postService.getPosts { posts ->
                        call.respond(posts)
                    }
                }
            }
        }

        route("/posts/{id}") {
            // Get Post by ID
            get {
                val postId = call.parameters["id"]
                launch {
                    postService.getPosts { posts ->
                        val post = posts.find { it.id == postId }
                        if (post != null) {
                            call.respond(post)
                        } else {
                            call.respond(HttpStatusCode.NotFound, "Post not found")
                        }
                    }
                }
            }

            // Add Comment to a Post
            post("comment") {
                authenticate {
                    val user = call.principal<MyAuthenticatedUser>()
                    if (user != null) {
                        val postId = call.parameters["id"]
                        val comment = call.receive<Comment>().copy(postId = postId!!)
                        launch {
                            commentService.addComment(comment)
                            call.respond(HttpStatusCode.Created, "Comment added successfully")
                        }
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "User is not authenticated")
                    }
                }
            }
        }

        // Get Comments for a Post
        route("/posts/{id}/comments") {
            get {
                val postId = call.parameters["id"]
                launch {
                    commentService.getComments(postId!!) { comments ->
                        call.respond(comments)
                    }
                }
            }
        }
    }
}


# Ktor Blog Application

This project is a simple blog application built with [Ktor](https://ktor.io/). It includes basic CRUD operations for posts and comments and uses Firebase authentication.

---

## Table of Contents
- [Project Structure](#project-structure)
- [Key Features](#key-features)
- [Code Explanation](#code-explanation)
- [How to Run](#how-to-run)

---

## Project Structure
The project is structured as follows:

```
├── controllers
│   └── configureRouting.kt
├── firebase
│   └── MyAuthenticatedUser.kt
├── models
│   ├── Post.kt
│   └── Comment.kt
├── services
│   ├── PostService.kt
│   └── CommentService.kt
└── Application.kt
```

### Key Files:
1. **controllers/configureRouting.kt**: Contains all the routes for handling posts and comments.
2. **firebase/MyAuthenticatedUser.kt**: Defines the authenticated user class for Firebase.
3. **models/Post.kt** and **models/Comment.kt**: Define the data models for posts and comments.
4. **services/PostService.kt** and **services/CommentService.kt**: Contain the business logic for managing posts and comments.

---

## Key Features
- **Create, Read, Update, Delete (CRUD) Posts**
- **Add Comments to Posts**
- **Get Comments for a Specific Post**
- **Firebase Authentication Integration**

---

## Code Explanation

### `configureRouting.kt`
This file defines the routes for the application. Here's a breakdown of its components:

#### Dependencies
```kotlin
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
```
These imports bring in necessary classes for routing, authentication, HTTP responses, and request handling.

#### Service Initialization
```kotlin
val postService = PostService()
val commentService = CommentService()
```
Instances of the `PostService` and `CommentService` classes are created to handle business logic.

#### Routing
The routing block defines all the endpoints for the blog application.

1. **Routes for Posts:**
   - **Create Post**:
     ```kotlin
     post {
         authenticate {
             val user = call.principal<MyAuthenticatedUser>()
             if (user != null) {
                 val post = call.receive<Post>()
                 postService.createPost(post.copy(authorId = user.id))
                 call.respond(HttpStatusCode.Created, "Post created successfully")
             } else {
                 call.respond(HttpStatusCode.Unauthorized, "User is not authenticated")
             }
         }
     }
     ```
     - Uses `authenticate` to verify the user's identity via Firebase.
     - Reads the post data from the request body and assigns the authenticated user's ID as the `authorId`.

   - **Get All Posts**:
     ```kotlin
     get {
         val posts = postService.getPosts()
         call.respond(posts)
     }
     ```
     - Fetches all posts from the service and returns them as a response.

   - **Get Post by ID**:
     ```kotlin
     get {
         val postId = call.parameters["id"]
         val post = postService.getPosts().find { it.id == postId }
         if (post != null) {
             call.respond(post)
         } else {
             call.respond(HttpStatusCode.NotFound, "Post not found")
         }
     }
     ```
     - Retrieves a specific post by its ID from the route parameter.

2. **Routes for Comments:**
   - **Add Comment to a Post**:
     ```kotlin
     post("comment") {
         authenticate {
             val user = call.principal<MyAuthenticatedUser>()
             if (user != null) {
                 val postId = call.parameters["id"]
                 val comment = call.receive<Comment>().copy(postId = postId!!)
                 commentService.addComment(comment)
                 call.respond(HttpStatusCode.Created, "Comment added successfully")
             } else {
                 call.respond(HttpStatusCode.Unauthorized, "User is not authenticated")
             }
         }
     }
     ```
     - Reads the comment data from the request body, associates it with the specified post ID, and adds it to the service.

   - **Get Comments for a Post**:
     ```kotlin
     get {
         val postId = call.parameters["id"]
         val comments = commentService.getComments(postId!!)
         call.respond(comments)
     }
     ```
     - Fetches all comments for a specific post.

### `PostService.kt` and `CommentService.kt`
These files contain the business logic for handling posts and comments. Methods like `createPost()`, `getPosts()`, `addComment()`, and `getComments()` perform operations on the database or an external API (e.g., Firebase).

### Models
1. **Post Model (`Post.kt`)**:
   ```kotlin
   data class Post(
       val id: String?,
       val title: String,
       val content: String,
       val authorId: String?
   )
   ```
   - Represents a blog post with fields like `id`, `title`, `content`, and `authorId`.

2. **Comment Model (`Comment.kt`)**:
   ```kotlin
   data class Comment(
       val id: String?,
       val content: String,
       val postId: String
   )
   ```
   - Represents a comment with fields like `id`, `content`, and `postId`.

---

## How to Run

### Prerequisites
- Kotlin installed
- Firebase project set up for authentication
- Database configured (if using Firebase Realtime Database or Firestore)

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo.git
   ```

2. Navigate to the project directory:
   ```bash
   cd ktor-blog
   ```

3. Configure Firebase:
   - Add your Firebase credentials to the project.
   - Update authentication logic in `MyAuthenticatedUser.kt`.

4. Run the application:
   ```bash
   ./gradlew run
   ```

5. Access the API:
   - Use tools like Postman or cURL to test the endpoints.
   - Example endpoints:
     - `POST /posts`
     - `GET /posts`
     - `GET /posts/{id}`
     - `POST /posts/{id}/comment`
     - `GET /posts/{id}/comments`

---

## Notes
- Ensure your Firebase rules are correctly set up to allow authenticated access.
- You can extend the application by adding features like updating or deleting posts and comments.

---

## License
This project is open-source and available under the [MIT License](LICENSE).

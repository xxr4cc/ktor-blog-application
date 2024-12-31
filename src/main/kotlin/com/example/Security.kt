package com.example

import com.example.firebase.MyAuthenticatedUser
import com.kborowy.authprovider.firebase.firebase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import java.io.File

fun Application.configureSecurity() {
    install(Authentication) {
        firebase {
            adminFile = File("src/main/resources/testddm2024-firebase-adminsdk-uy7sr-496ef781b0.json")
            realm = "My Firebase Auth"
            validate { token ->
                MyAuthenticatedUser(
                    id = token.uid,
                    email = token.email,
                    displayName = token.name
                )
            }
        }
    }
}

package com.example

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

fun Application.configureTemplating() {
    install(ContentNegotiation) {
        html()  // For server-side HTML rendering (optional)
    }
}

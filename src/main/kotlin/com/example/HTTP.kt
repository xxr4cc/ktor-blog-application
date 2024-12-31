package com.example

import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.http.content.*

fun Application.configureHTTP() {
    install(ContentNegotiation) {
        // Add serialization support, if needed
    }

    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respondText("Internal server error: ${cause.localizedMessage}")
        }
    }
}

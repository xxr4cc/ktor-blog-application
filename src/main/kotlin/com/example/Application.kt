package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun Application.module() {
    configureSecurity() // Configure security (authentication)
    configureRouting()  // Configure routing (API endpoints)
    configureSerialization() // Optional: For serializing data (JSON, etc.)
}

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

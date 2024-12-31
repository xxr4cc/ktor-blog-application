
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
}

dependencies {
    // Ktor Dependencies
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.default.headers)
    implementation(libs.ktor.server.html.builder)
    implementation(libs.ktor.server.status.pages)

    // Firebase Admin SDK
    implementation("com.google.firebase:firebase-admin:9.1.0") // Firebase Admin SDK for Firestore & Authentication

    // Kotlin and HTML dependencies
    implementation(libs.kotlinx.html)
    implementation(libs.kotlin.css)

    // Logging
    implementation(libs.logback.classic)

    // Test Dependencies
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}

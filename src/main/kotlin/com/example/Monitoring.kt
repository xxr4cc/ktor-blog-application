package com.example

import io.ktor.server.application.*
import io.ktor.server.metrics.prometheus.*

fun Application.configureMonitoring() {
    install(PrometheusMetrics) {
        // Configure Prometheus monitoring if needed
    }
}

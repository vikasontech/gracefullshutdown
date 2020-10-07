package com.example.gracefullshutdown

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConstructorBinding
@ConfigurationProperties(prefix = "application")
data class ApplicationConfig(
    val unloadDelay: Duration
)
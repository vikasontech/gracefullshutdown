package com.example.gracefullshutdown

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "application")
data class ApplicationConfig(
    val unloadDelay: String
) {
    fun getUnloadDelayTimeInMs(): Long = ConvertAlphaNumericTimeToMS.convert(unloadDelay)
}
package com.example.gracefullshutdown

import org.apache.catalina.core.StandardContext
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@SpringBootApplication
@EnableConfigurationProperties(value = [ApplicationConfig::class])
class GraceFullShutdownExample

fun main(args: Array<String>) {
    runApplication<GraceFullShutdownExample>(*args)
}

@Configuration
class ServletContainerConfiguration {

    @Bean
    @Suppress("unused")
    fun servletWebServerFactory(applicationConfig: ApplicationConfig): ServletWebServerFactory {
        val serverFactory = TomcatServletWebServerFactory()
        serverFactory.addContextCustomizers(TomcatContextCustomizer {
            if (it is StandardContext) {
                val context = it
                log.info("Current context unloadDelay: {}", context.unloadDelay)
                context.unloadDelay = Duration.ofMinutes(2).toMillis()
                log.info("Updated context unloadDelay: {}", context.unloadDelay)
            }
        })
        return serverFactory
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}


@RestController
class LongPauseController (){

    @GetMapping("/test")
    fun longPause(): ResponseEntity<String> { log.info("I AM STILL PROCESSING WILL TAKE AROUND 60 SECS. TO FINISH. PLEASE WAIT ....")
        Thread.sleep(60 * 1000)
        log.info("FINALLY! I AM DONE. YOU CAN SHUTDOWN NOW. :)")
        return ResponseEntity.ok("Thank you for waiting.")
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}


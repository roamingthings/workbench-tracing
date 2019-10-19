package de.roamingthings.tracing.textlibrary.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.SecureRandom
import java.util.Random

@Configuration
class RandomConfiguration {
    @Bean
    fun randomNumberGenerator(): Random {
        return SecureRandom()
    }
}

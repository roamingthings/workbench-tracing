package de.roamingthings.tracing.novelai.configuration

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

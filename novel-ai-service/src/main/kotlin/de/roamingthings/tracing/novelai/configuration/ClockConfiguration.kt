package de.roamingthings.tracing.novelai.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import java.time.Clock

import java.time.Clock.systemDefaultZone

@Configuration
class ClockConfiguration {
    @Bean
    fun systemClock(): Clock {
        return systemDefaultZone()
    }
}

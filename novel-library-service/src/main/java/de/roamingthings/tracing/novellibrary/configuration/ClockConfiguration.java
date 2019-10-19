package de.roamingthings.tracing.novellibrary.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

import static java.time.Clock.systemDefaultZone;

@Configuration
public class ClockConfiguration {
    @Bean
    public Clock systemClock() {
        return systemDefaultZone();
    }
}

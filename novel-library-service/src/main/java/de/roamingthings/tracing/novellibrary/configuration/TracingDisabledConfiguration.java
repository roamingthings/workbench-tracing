package de.roamingthings.tracing.novellibrary.configuration;

import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.reporters.InMemoryReporter;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Tracer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(value = "opentracing.jaeger.enabled", havingValue = "false", matchIfMissing = false)
@Configuration
public class TracingDisabledConfiguration {
    @Bean
    public Tracer jaegerTracer() {
        InMemoryReporter reporter = new InMemoryReporter();
        ConstSampler sampler = new ConstSampler(false);
        return new JaegerTracer.Builder("untraced-service")
                .withReporter(reporter)
                .withSampler(sampler)
                .build();
    }
}

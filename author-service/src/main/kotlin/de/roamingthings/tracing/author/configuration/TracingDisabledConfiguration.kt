package de.roamingthings.tracing.author.configuration

import io.jaegertracing.internal.JaegerTracer
import io.jaegertracing.internal.reporters.InMemoryReporter
import io.jaegertracing.internal.samplers.ConstSampler
import io.opentracing.Tracer
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConditionalOnProperty(value = ["opentracing.jaeger.enabled"], havingValue = "false", matchIfMissing = false)
@Configuration
class TracingDisabledConfiguration {
    @Bean
    fun jaegerTracer(): Tracer {
        val reporter = InMemoryReporter()
        val sampler = ConstSampler(false)
        return JaegerTracer.Builder("untraced-service")
                .withReporter(reporter)
                .withSampler(sampler)
                .build();
    }
}

package de.roamingthings.tracing.novelai.test

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.opentracing.Scope
import io.opentracing.ScopeManager
import io.opentracing.Span
import io.opentracing.Tracer
import org.mockito.Mockito

fun mockTracingLenientUsingMock(tracer: Tracer) {
    val spanMock = mock<Span>(lenient = true)
    val scopeMock = mock<Scope>(lenient = true)
    val scopeManagerMock = mock<ScopeManager>(lenient = true)
    val builderMock = mock<Tracer.SpanBuilder>(lenient = true) {
        on { start() } doReturn spanMock
        on { startActive(any()) } doReturn scopeMock
    }
    Mockito.lenient().doReturn(scopeManagerMock)
            .`when`(tracer).scopeManager()
    Mockito.lenient().doReturn(builderMock)
            .`when`(tracer).buildSpan(any())
    Mockito.lenient().doReturn(spanMock)
            .`when`(tracer).activeSpan()
    Mockito.lenient().doReturn(scopeMock)
            .`when`(tracer).activateSpan(any())
}

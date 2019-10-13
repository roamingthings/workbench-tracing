package de.roamingthings.tracing.testing.mock

enum class ServiceMockPorts(private val port: Int) {
    TEXT_LIBRARY_SERVICE_MOCK(10082);

    fun port(): Int {
        return port
    }
}

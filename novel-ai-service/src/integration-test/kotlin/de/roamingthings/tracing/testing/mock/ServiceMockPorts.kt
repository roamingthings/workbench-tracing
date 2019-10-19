package de.roamingthings.tracing.testing.mock

enum class ServiceMockPorts(private val port: Int) {
    AUTHOR_SERVICE_MOCK(10081),
    NOVEL_LIBRARY_SERVICE_MOCK(10083);

    fun port(): Int {
        return port
    }
}

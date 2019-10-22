package de.roamingthings.tracing.testing.mock

enum class ServiceMockPorts(private val port: Int) {
    AUTHOR_SERVICE_MOCK(10081),
    TEXT_LIBRARY_SERVICE_MOCK(10082),
    NOVEL_LIBRARY_SERVICE_MOCK(10083),
    DOCUMENT_GENERATOR_SERVICE_MOCK(10084);

    fun port(): Int {
        return port
    }
}

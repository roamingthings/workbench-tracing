package de.roamingthings.tracing.testing.mock

import de.roamingthings.tracing.testing.mock.AuthorServiceMock.Companion.authorServiceMock
import org.junit.jupiter.api.BeforeEach

abstract class WireMockTestBase {
    @BeforeEach
    fun setupForWireMock() {
        cleanup()
    }

    private fun cleanup() {
        resetMocks()
    }

    private fun resetMocks() {
        authorServiceMock.startIfNotRunning()
        authorServiceMock.reset()
    }
}

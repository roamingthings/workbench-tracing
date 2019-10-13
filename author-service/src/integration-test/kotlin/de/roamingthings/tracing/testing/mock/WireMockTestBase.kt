package de.roamingthings.tracing.testing.mock

import de.roamingthings.tracing.testing.mock.TextLibraryServiceMock.Companion.textLibraryServiceMock
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
        textLibraryServiceMock.startIfNotRunning()
        textLibraryServiceMock.reset()
    }
}

package de.roamingthings.tracing.testing.mock

import de.roamingthings.tracing.testing.mock.AuthorServiceMock.Companion.authorServiceMock
import de.roamingthings.tracing.testing.mock.DocumentGeneratorServiceMock.Companion.documentGeneratorServiceMock
import de.roamingthings.tracing.testing.mock.NovelLibraryServiceMock.Companion.novelLibraryServiceMock
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
        authorServiceMock.startIfNotRunning()
        authorServiceMock.reset()
        novelLibraryServiceMock.startIfNotRunning()
        novelLibraryServiceMock.reset()
        textLibraryServiceMock.startIfNotRunning()
        textLibraryServiceMock.reset()
        documentGeneratorServiceMock.startIfNotRunning()
        documentGeneratorServiceMock.reset()
    }
}

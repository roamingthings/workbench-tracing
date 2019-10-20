package de.roamingthings.tracing.testing.mock

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import javax.servlet.http.HttpServletResponse.SC_OK

class TextLibraryServiceMock : BaseWireMock(ServiceMockPorts.TEXT_LIBRARY_SERVICE_MOCK.port()) {
    companion object {
        val textLibraryServiceMock = TextLibraryServiceMock()
    }

    override fun wireMockOptions(port: Int): WireMockConfiguration {
        return super.wireMockOptions(port).usingFilesUnderClasspath("wiremock/textlibrary")
    }

    fun retrievesRandomParagraph() {
        wireMock.register(get(urlPathMatching("/paragraphs/random"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", TEXT_PLAIN_VALUE)
                        .withStatus(SC_OK)
                .withBodyFile("get-random-paragraph.txt"))
        )
    }
}

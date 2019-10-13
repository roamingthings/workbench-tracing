package de.roamingthings.tracing.testing.mock

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import de.roamingthings.tracing.testing.mock.ServiceMockPorts.TEXT_LIBRARY_SERVICE_MOCK
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import javax.servlet.http.HttpServletResponse.SC_OK

class TextLibraryServiceMock : BaseWireMock(TEXT_LIBRARY_SERVICE_MOCK.port()) {
    companion object {
        val textLibraryServiceMock = TextLibraryServiceMock()
    }

    override fun wireMockOptions(port: Int): WireMockConfiguration {
        return super.wireMockOptions(port).usingFilesUnderClasspath("wiremock/text-library")
    }

    fun serviceGetsRandomParagraph() {
        wireMock.register(get(urlEqualTo("/paragraphs/random"))
                .willReturn(aResponse()
                        .withStatus(SC_OK)
                        .withBodyFile("get-paragraph.txt")
                        .withHeader("Content-Type", TEXT_PLAIN_VALUE)))
    }
}

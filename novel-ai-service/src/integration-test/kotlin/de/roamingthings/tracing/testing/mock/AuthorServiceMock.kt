package de.roamingthings.tracing.testing.mock

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import de.roamingthings.tracing.testing.mock.ServiceMockPorts.AUTHOR_SERVICE_MOCK
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import javax.servlet.http.HttpServletResponse.SC_OK

class AuthorServiceMock : BaseWireMock(AUTHOR_SERVICE_MOCK.port()) {
    companion object {
        val authorServiceMock = AuthorServiceMock()
    }

    override fun wireMockOptions(port: Int): WireMockConfiguration {
        return super.wireMockOptions(port).usingFilesUnderClasspath("wiremock/author")
    }

    fun serviceGeneratesContent() {
        wireMock.register(post(urlEqualTo("/contents"))
                .willReturn(aResponse()
                        .withStatus(SC_OK)
                        .withBodyFile("post-contents.txt")
                        .withHeader("Content-Type", TEXT_PLAIN_VALUE)))
    }
}

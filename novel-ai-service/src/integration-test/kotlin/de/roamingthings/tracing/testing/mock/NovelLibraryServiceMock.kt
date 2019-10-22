package de.roamingthings.tracing.testing.mock

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.put
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import de.roamingthings.tracing.testing.mock.ServiceMockPorts.NOVEL_LIBRARY_SERVICE_MOCK
import javax.servlet.http.HttpServletResponse.SC_OK

class NovelLibraryServiceMock : BaseWireMock(NOVEL_LIBRARY_SERVICE_MOCK.port()) {
    companion object {
        val novelLibraryServiceMock = NovelLibraryServiceMock()
    }

    override fun wireMockOptions(port: Int): WireMockConfiguration {
        return super.wireMockOptions(port).usingFilesUnderClasspath("wiremock/novellibrary")
    }

    fun serviceStoresNovel() {
        wireMock.register(put(urlPathMatching("/novels/[0-9a-f-]+"))
                .willReturn(aResponse()
                        .withStatus(SC_OK)))
    }

    fun serviceRetrievesNovel() {
        wireMock.register(get(urlPathMatching("/novels/[0-9a-f-]+"))
                .willReturn(aResponse()
                        .withStatus(SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("get-novel.json")
                ))
    }
}

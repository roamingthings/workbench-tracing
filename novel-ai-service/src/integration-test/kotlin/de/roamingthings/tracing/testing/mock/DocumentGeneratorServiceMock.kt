package de.roamingthings.tracing.testing.mock

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.equalToJson
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.put
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import de.roamingthings.tracing.testing.mock.ServiceMockPorts.DOCUMENT_GENERATOR_SERVICE_MOCK
import de.roamingthings.tracing.testing.mock.ServiceMockPorts.NOVEL_LIBRARY_SERVICE_MOCK
import javax.servlet.http.HttpServletResponse.SC_OK

class DocumentGeneratorServiceMock : BaseWireMock(DOCUMENT_GENERATOR_SERVICE_MOCK.port()) {
    companion object {
        val documentGeneratorServiceMock = DocumentGeneratorServiceMock()
    }

    override fun wireMockOptions(port: Int): WireMockConfiguration {
        return super.wireMockOptions(port).usingFilesUnderClasspath("wiremock/documentgenerator")
    }

    fun serviceGeneratesDocument() {
        wireMock.register(post(urlEqualTo("/documents"))
                .withHeader("Accept", equalTo("text/asciidoc"))
                .withRequestBody(equalToJson("{\n  \"title\": \"The Novel Title\",\n  \"content\": \"The novel paragraph.\\n\\nAnother Paragraph.\"\n}"))
                .willReturn(aResponse()
                        .withStatus(SC_OK)
                        .withHeader("Content-Type", "text/asciidoc")
                        .withBodyFile("post-documents.txt")
                ))
    }
}

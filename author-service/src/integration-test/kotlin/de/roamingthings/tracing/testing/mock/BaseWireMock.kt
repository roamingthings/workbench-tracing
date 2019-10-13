package de.roamingthings.tracing.testing.mock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.common.Slf4jNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import org.slf4j.LoggerFactory.getLogger
import java.lang.Thread.sleep
import java.util.HashMap

abstract class BaseWireMock internal constructor(private val port: Int) {
    companion object {
        private val log = getLogger(BaseWireMock::class.java)
        private val servers = HashMap<Int, WireMockServer>()
    }

    val wireMock: WireMock

    init {
        var wireMockServer: WireMockServer? = servers[port]
        if (wireMockServer == null) {
            wireMockServer = WireMockServer(wireMockOptions(port))
            servers[port] = wireMockServer
            wireMockServer.start()
        }
        wireMock = WireMock("localhost", port)
    }

    internal open fun wireMockOptions(port: Int): WireMockConfiguration {
        return options().port(port).notifier(Slf4jNotifier(true))
    }

    open fun setupDefaultStubs() {
        // NOOP
    }

    fun startIfNotRunning() {
        val server = servers[port]
        if (server != null && !server.isRunning) {
            log.info("Starting Mock on port {}", port)
            server.start()
            resetWireMockServerMappings(server)
        }
    }

    private fun resetWireMockServerMappings(server: WireMockServer) {
        for (i in 0..99) {
            try {
                server.resetToDefaultMappings()
                setupDefaultStubs()
                log.debug("Reset mock on port {} after {}*100ms", port, i)
                return
            } catch (e: Exception) {
                log.info("While setting up WireMock: {}", e.message)
                try {
                    sleep(100)
                } catch (ignore: InterruptedException) {
                    Thread.currentThread().interrupt()
                    break
                }

            }

        }
        log.error("Could not reset WireMockSettings on port {} in 10s ~> Mock may be unresponsive", port)
    }

    fun reset() {
        val server = servers[port]
        if (server != null && server.isRunning) {
            resetWireMockServerMappings(server)
        }
    }

    internal fun verifyThat(requestPatternBuilder: RequestPatternBuilder) {
        wireMock.verifyThat(requestPatternBuilder)
    }

    internal fun verifyThat(count: Int, requestPatternBuilder: RequestPatternBuilder) {
        wireMock.verifyThat(count, requestPatternBuilder)
    }

    internal fun verifyNever(requestPatternBuilder: RequestPatternBuilder) {
        wireMock.verifyThat(0, requestPatternBuilder)
    }
}

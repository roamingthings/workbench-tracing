package de.roamingthings.tracing.textlibrary

import com.nhaarman.mockitokotlin2.any
import de.roamingthings.tracing.textlibrary.ports.driven.DbText
import de.roamingthings.tracing.textlibrary.ports.driven.MongoTextRepository
import org.assertj.core.api.SoftAssertions
import org.bson.conversions.Bson
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles
import java.util.Random

@SpringBootTest
@ActiveProfiles("integrationtest")
class MongoTextRepositoryIT {
    @Autowired
    lateinit var mongoTextRepository: MongoTextRepository

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @MockBean
    lateinit var randomNumberGenerator: Random

    @Test
    fun `should load random text`() {
        somePersistedText()
        randomNumberGeneratorGeneratesRandomNumbers()

        val text1 = mongoTextRepository.loadRandomText()
        val text2 = mongoTextRepository.loadRandomText()

        SoftAssertions.assertSoftly { softly ->
            softly.assertThat(text1).isEqualTo("Lorem")
            softly.assertThat(text2).isEqualTo("Ipsum")
            softly.assertThat(text1).isNotEqualTo(text2)
        }
    }

    private fun randomNumberGeneratorGeneratesRandomNumbers() {
        `when`(randomNumberGenerator.nextInt(any()))
                .thenAnswer(object: Answer<Int> {
                    private var count = 0
                    private val answers = arrayOf(1, 4)

                    override fun answer(invocation: InvocationOnMock?): Int {
                        return answers[count++ % 2]
                    }
                })
    }

    private fun somePersistedText() {
        mongoTemplate.dropCollection("texts")
        mongoTemplate.insert(DbText(1, "Lorem"), "texts")
        mongoTemplate.insert(DbText(4, "Ipsum"), "texts")
    }
}

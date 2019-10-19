package de.roamingthings.tracing.textlibrary.ports.driven

import org.bson.Document
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.Random

@Repository
class MongoTextRepository(
        private val mongoTemplate: MongoTemplate,
        private val randomNumberGenerator: Random
): TextRepository {
    companion object {
        private val log = LoggerFactory.getLogger(MongoTextRepository::class.java)
    }

    override fun loadRandomText(): String {
        val ordinal = randomNumberGenerator.nextInt(countTexts())

        log.info("Fetching text with ordinal $ordinal")

        val query = queryForTextWithOrdinal(ordinal)
        val document = mongoTemplate.findOne(query, Document::class.java, "texts")

        return if (document != null && document["text"] != null) document.getString("text") else ""
    }

    private fun queryForTextWithOrdinal(ordinal: Int) = Query().addCriteria(Criteria.where("ordinal").`is`(ordinal))

    private fun countTexts() = mongoTemplate.getCollection("texts").countDocuments().toInt()
}

package marketplace.web.infrastructure

import marketplace.domain.ContentModeration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

/**
 * PurgoMalum is a simple, free, RESTful web service for filtering and removing content of profanity, obscenity and
 * other unwanted text.
 *
 * See: [http://www.purgomalum.com]
 */
@Service
class PurgomalumClient(restTemplateBuilder: RestTemplateBuilder) : ContentModeration {
    private val restTemplate = restTemplateBuilder.build()

    override fun checkTextForProfanity(text: String): Boolean {
        val result = restTemplate.getForObject(
            UriComponentsBuilder.fromHttpUrl("https://www.purgomalum.com/service/containsprofanity")
                .queryParam("text", text)
                .toUriString(),
            String::class.java
        )

        return result.toBoolean()
    }
}

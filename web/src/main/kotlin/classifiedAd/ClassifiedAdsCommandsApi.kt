package marketplace.web.classifiedAd

import marketplace.web.classifiedAd.Contracts.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception

@RestController
@RequestMapping("/ad")
class ClassifiedAdsCommandsApi(private val applicationService: ClassifiedAdApplicationService) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun post(request: V1.Create): ResponseEntity<String> =
        handleRequest(request, applicationService::handle)

    @PutMapping("name")
    fun put(request: V1.SetTitle): ResponseEntity<String> =
        handleRequest(request, applicationService::handle)

    @PutMapping("text")
    fun put(request: V1.UpdateText): ResponseEntity<String> =
        handleRequest(request, applicationService::handle)

    @PutMapping("price")
    fun put(request: V1.UpdatePrice): ResponseEntity<String> =
        handleRequest(request, applicationService::handle)

    @PutMapping("publish")
    fun put(request: V1.RequestToPublish): ResponseEntity<String> =
        handleRequest(request, applicationService::handle)

    private fun <T> handleRequest(request: T, handler: (T) -> Unit): ResponseEntity<String> =
        try {
            logger.debug("Handling HTTP request of type ${handler.javaClass.name}")
            handler(request)
            ResponseEntity.ok().build()
        }
        catch (exception: Exception) {
            logger.error("Error handling the request", exception)
            ResponseEntity.badRequest().body(exception.toString())
        }
}

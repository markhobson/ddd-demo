package marketplace.web.classifiedAd

import marketplace.web.classifiedAd.Contracts.V1
import marketplace.web.infrastructure.RequestHandler.handleRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ad")
class ClassifiedAdsCommandsApi(private val applicationService: ClassifiedAdApplicationService) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun post(request: V1.Create): ResponseEntity<String> =
        handleRequest(request, applicationService::handle, logger)

    @PutMapping("name")
    fun put(request: V1.SetTitle): ResponseEntity<String> =
        handleRequest(request, applicationService::handle, logger)

    @PutMapping("text")
    fun put(request: V1.UpdateText): ResponseEntity<String> =
        handleRequest(request, applicationService::handle, logger)

    @PutMapping("price")
    fun put(request: V1.UpdatePrice): ResponseEntity<String> =
        handleRequest(request, applicationService::handle, logger)

    @PutMapping("publish")
    fun put(request: V1.RequestToPublish): ResponseEntity<String> =
        handleRequest(request, applicationService::handle, logger)
}

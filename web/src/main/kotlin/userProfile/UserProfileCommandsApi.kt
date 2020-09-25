package marketplace.web.userProfile

import marketplace.web.infrastructure.RequestHandler.handleRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")
class UserProfileCommandsApi(private val applicationService: UserProfileApplicationService) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun post(request: Contracts.V1.RegisterUser): ResponseEntity<String> =
        handleRequest(request, applicationService::handle, logger)

    @PutMapping("fullname")
    fun put(request: Contracts.V1.UpdateUserFullName): ResponseEntity<String> =
        handleRequest(request, applicationService::handle, logger)

    @PutMapping("displayname")
    fun put(request: Contracts.V1.UpdateUserDisplayName): ResponseEntity<String> =
        handleRequest(request, applicationService::handle, logger)

    @PutMapping("photo")
    fun put(request: Contracts.V1.UpdateUserProfilePhoto): ResponseEntity<String> =
        handleRequest(request, applicationService::handle, logger)
}

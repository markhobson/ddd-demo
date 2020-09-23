package marketplace.web.api

import marketplace.web.contracts.ClassifiedAds
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ad")
class ClassifiedAdsCommandsApi {
    @PostMapping
    fun post(request: ClassifiedAds.V1.Create): ResponseEntity<String> {
        // TODO: handle the request here
        return ResponseEntity.ok().build()
    }
}

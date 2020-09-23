package marketplace.web.api

import marketplace.web.contracts.ClassifiedAds.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ad")
class ClassifiedAdsCommandsApi(private val applicationService: ClassifiedAdApplicationService) {
    @PostMapping
    fun post(request: V1.Create): ResponseEntity<String> {
        applicationService.handle(request)
        return ResponseEntity.ok().build()
    }

    @PutMapping("name")
    fun put(request: V1.SetTitle): ResponseEntity<String> {
        applicationService.handle(request)
        return ResponseEntity.ok().build()
    }

    @PutMapping("text")
    fun put(request: V1.UpdateText): ResponseEntity<String> {
        applicationService.handle(request)
        return ResponseEntity.ok().build()
    }

    @PutMapping("price")
    fun put(request: V1.UpdatePrice): ResponseEntity<String> {
        applicationService.handle(request)
        return ResponseEntity.ok().build()
    }

    @PutMapping("publish")
    fun put(request: V1.RequestToPublish): ResponseEntity<String> {
        applicationService.handle(request)
        return ResponseEntity.ok().build()
    }
}

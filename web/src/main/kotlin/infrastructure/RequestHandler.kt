package marketplace.web.infrastructure

import org.slf4j.Logger
import org.springframework.http.ResponseEntity

object RequestHandler {
    fun <T> handleRequest(request: T, handler: (T) -> Unit, logger: Logger): ResponseEntity<String> =
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

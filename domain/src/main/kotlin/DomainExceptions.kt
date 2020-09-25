package marketplace.domain

object DomainExceptions {
    class InvalidEntityStateException(entity: Any, message: String)
        : Exception("Entity ${entity.javaClass.name} state change rejected, $message")

    class ProfanityFound(text: String) : Exception("Profanity found in text: $text")
}

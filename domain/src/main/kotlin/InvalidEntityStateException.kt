package marketplace.domain

class InvalidEntityStateException(entity: Any, message: String)
    : Exception("Entity ${entity.javaClass.name} state change rejected, $message")

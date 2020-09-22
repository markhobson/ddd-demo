package marketplace.domain

import java.lang.Exception

class InvalidEntityStateException(entity: Any, message: String)
    : Exception("Entity ${entity.javaClass.name} state change rejected, $message")

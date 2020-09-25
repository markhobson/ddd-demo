package marketplace.domain

import marketplace.framework.Ids
import java.util.UUID

data class UserId(val value: UUID) {
    constructor() : this(Ids.ZERO)
}

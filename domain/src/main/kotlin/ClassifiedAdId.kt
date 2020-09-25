package marketplace.domain

import marketplace.framework.Ids
import java.util.UUID

data class ClassifiedAdId(val value: UUID) {
    constructor() : this(Ids.ZERO)
}

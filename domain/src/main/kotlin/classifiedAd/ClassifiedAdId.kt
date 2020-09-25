package marketplace.domain.classifiedAd

import marketplace.framework.Ids
import java.util.UUID

data class ClassifiedAdId(val value: UUID) {
    constructor() : this(Ids.ZERO)
}

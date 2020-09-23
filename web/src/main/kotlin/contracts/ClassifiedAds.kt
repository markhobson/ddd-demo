package marketplace.web.contracts

import java.util.UUID

class ClassifiedAds {
    class V1 {
        data class Create(val id: UUID, val ownerId: UUID)
    }
}

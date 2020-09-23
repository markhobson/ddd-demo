package marketplace.web.contracts

import java.util.UUID

class ClassifiedAds {
    class V1 {
        data class Create(val id: UUID, val ownerId: UUID)

        data class SetTitle(val id: UUID, val title: String)

        data class UpdateText(val id: UUID, val text: String)

        data class UpdatePrice(val id: UUID, val price: Double, val currency: String)

        data class RequestToPublish(val id: UUID)
    }
}

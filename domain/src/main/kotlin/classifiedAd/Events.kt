package marketplace.domain.classifiedAd

import java.math.BigDecimal
import java.util.UUID

class Events {
    data class ClassifiedAdCreated(val id: UUID, val ownerId: UUID)

    data class ClassifiedAdTitleChanged(val id: UUID, val title: String)

    data class ClassifiedAdTextUpdated(val id: UUID, val adText: String)

    data class ClassifiedAdPriceUpdated(val id: UUID, val price: BigDecimal, val currencyCode: String)

    data class ClassifiedAdSentForReview(val id: UUID)

    data class PictureAddedToAClassifiedAd(val classifiedAdId: UUID, val pictureId: UUID, val url: String,
        val height: Int, val width: Int, val order: Int)

    data class ClassifiedAdPictureResized(val pictureId: UUID, val height: Int, val width: Int)
}

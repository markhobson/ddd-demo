package marketplace.domain.classifiedAd

import marketplace.domain.InvalidEntityStateException
import marketplace.domain.UserId
import marketplace.framework.AggregateRoot
import java.math.BigDecimal
import java.net.URI
import java.util.UUID

class ClassifiedAd(id: ClassifiedAdId, ownerId: UserId) : AggregateRoot<ClassifiedAdId>(ClassifiedAdId()) {
    var ownerId: UserId = UserId()
        private set

    var title: ClassifiedAdTitle? = null
        private set

    var text: ClassifiedAdText? = null
        private set

    var price: Price? = null
        private set

    var state: ClassifiedAdState = ClassifiedAdState.INACTIVE
        private set

    var approvedBy: UserId? = null
        private set

    private val mutablePictures: MutableList<Picture> = mutableListOf()

    val pictures: List<Picture> = mutablePictures

    init {
        apply(Events.ClassifiedAdCreated(id.value, ownerId.value))
    }

    fun setTitle(title: ClassifiedAdTitle) {
        apply(Events.ClassifiedAdTitleChanged(id.value, title.value))
    }

    fun updateText(text: ClassifiedAdText) {
        apply(Events.ClassifiedAdTextUpdated(id.value, text.value))
    }

    fun updatePrice(price: Price) {
        apply(Events.ClassifiedAdPriceUpdated(id.value, price.money.amount, price.money.currency.currencyCode))
    }

    fun requestToPublish() {
        apply(Events.ClassifiedAdSentForReview(id.value))
    }

    fun addPicture(pictureUri: URI, size: PictureSize) {
        apply(Events.PictureAddedToAClassifiedAd(
            classifiedAdId = id.value,
            pictureId = UUID.randomUUID(),
            url = pictureUri.toString(),
            height = size.height,
            width = size.width,
            order = (pictures.maxOfOrNull { picture -> picture.order } ?: 0) + 1
        ))
    }

    fun resizePicture(pictureId: PictureId, newSize: PictureSize) {
        val picture = findPicture(pictureId)
            ?: throw IllegalArgumentException("Cannot resize a picture that I don't have")
        picture.resize(newSize)
    }

    private fun findPicture(id: PictureId) =
        pictures.find { picture -> picture.id == id }

    private fun firstPicture() =
        pictures.firstOrNull()

    override fun on(event: Any) {
        when (event) {
            is Events.ClassifiedAdCreated -> {
                id = ClassifiedAdId(event.id)
                ownerId = UserId(event.ownerId)
                state = ClassifiedAdState.INACTIVE
            }
            is Events.ClassifiedAdTitleChanged -> {
                title = ClassifiedAdTitle(event.title)
            }
            is Events.ClassifiedAdTextUpdated -> {
                text = ClassifiedAdText(event.adText)
            }
            is Events.ClassifiedAdPriceUpdated -> {
                price = Price.deserialize(event.price, event.currencyCode)
            }
            is Events.ClassifiedAdSentForReview -> {
                state = ClassifiedAdState.PENDING_REVIEW
            }
            is Events.PictureAddedToAClassifiedAd -> {
                val newPicture = Picture(::apply)
                applyToEntity(newPicture, event)
                mutablePictures.add(newPicture)
            }
        }
    }

    override fun ensureValidState() {
        val valid = when (state) {
            ClassifiedAdState.PENDING_REVIEW ->
                title != null
                && text != null
                && price?.let { it.money.amount > BigDecimal.ZERO } ?: false
                && firstPicture().hasCorrectSize()
            ClassifiedAdState.ACTIVE ->
                title != null
                && text != null
                && price?.let { it.money.amount > BigDecimal.ZERO } ?: false
                && firstPicture().hasCorrectSize()
                && approvedBy != null
            else -> true
        }

        if (!valid) {
            throw InvalidEntityStateException(this, "Post-checks failed in state $state")
        }
    }

    companion object {
        fun deserialize(
            id: ClassifiedAdId,
            ownerId: UserId,
            title: ClassifiedAdTitle?,
            text: ClassifiedAdText?,
            price: Price?,
            state: ClassifiedAdState,
            approvedBy: UserId?,
            pictures: List<Picture>
        ): ClassifiedAd =
            ClassifiedAd(id, ownerId).also {
                it.title = title
                it.text = text
                it.price = price
                it.state = state
                it.approvedBy = approvedBy
                it.mutablePictures.clear()
                it.mutablePictures.addAll(pictures)
            }
    }

    enum class ClassifiedAdState {
        PENDING_REVIEW,
        ACTIVE,
        INACTIVE,
        MARKED_AS_SOLD
    }
}

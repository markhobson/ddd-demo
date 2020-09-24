package marketplace.domain

import marketplace.framework.AggregateRoot
import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.net.URI
import java.util.UUID

class ClassifiedAd(id: ClassifiedAdId, ownerId: UserId) : AggregateRoot<ClassifiedAdId>(id) {
    var ownerId = ownerId
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

    private val pictures: MutableList<Picture> = mutableListOf()

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
            order = pictures.maxOf { picture -> picture.order } + 1
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
                price = Price(event.price, event.currencyCode)
            }
            is Events.ClassifiedAdSentForReview -> {
                state = ClassifiedAdState.PENDING_REVIEW
            }
            is Events.PictureAddedToAClassifiedAd -> {
                val newPicture = Picture(::apply)
                applyToEntity(newPicture, event)
                pictures.add(newPicture)
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

    enum class ClassifiedAdState {
        PENDING_REVIEW,
        ACTIVE,
        INACTIVE,
        MARKED_AS_SOLD
    }
}

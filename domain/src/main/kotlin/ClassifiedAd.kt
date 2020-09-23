package marketplace.domain

import marketplace.framework.Entity
import java.math.BigDecimal

class ClassifiedAd(id: ClassifiedAdId, ownerId: UserId) : Entity() {
    var id = id
        private set

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
        }
    }

    override fun ensureValidState() {
        val valid = when (state) {
            ClassifiedAdState.PENDING_REVIEW ->
                title != null
                && text != null
                && price?.let { it.money.amount > BigDecimal.ZERO } ?: false
            ClassifiedAdState.ACTIVE ->
                title != null
                && text != null
                && price?.let { it.money.amount > BigDecimal.ZERO } ?: false
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

package marketplace.domain

import marketplace.framework.Entity
import java.math.BigDecimal

class ClassifiedAd(val id: ClassifiedAdId, val ownerId: UserId) : Entity() {
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
        ensureValidState()
        raise(Events.ClassifiedAdCreated(id.value, ownerId.value))
    }

    fun setTitle(title: ClassifiedAdTitle) {
        this.title = title
        ensureValidState()
        raise(Events.ClassifiedAdTitleChanged(id.value, title.value))
    }

    fun updateText(text: ClassifiedAdText) {
        this.text = text
        ensureValidState()
        raise(Events.ClassifiedAdTextUpdated(id.value, text.value))
    }

    fun updatePrice(price: Price) {
        this.price = price
        ensureValidState()
        raise(Events.ClassifiedAdPriceUpdated(id.value, price.money.amount, price.money.currency.currencyCode))
    }

    fun requestToPublish() {
        state = ClassifiedAdState.PENDING_REVIEW
        ensureValidState()
        raise(Events.ClassifiedAdSentForReview(id.value))
    }

    protected fun ensureValidState() {
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

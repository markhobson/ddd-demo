package marketplace.domain

import java.math.BigDecimal

class ClassifiedAd(val id: ClassifiedAdId, val ownerId: UserId) {
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
    }

    fun setTitle(title: ClassifiedAdTitle) {
        this.title = title
        ensureValidState()
    }

    fun updateText(text: ClassifiedAdText) {
        this.text = text
        ensureValidState()
    }

    fun updatePrice(price: Price) {
        this.price = price
        ensureValidState()
    }

    fun requestToPublish() {
        state = ClassifiedAdState.PENDING_REVIEW
        ensureValidState()
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

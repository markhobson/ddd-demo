package marketplace.domain

import java.math.BigDecimal

class ClassifiedAd(val id: ClassifiedAdId, val ownerId: UserId) {
    private var title: String? = null

    private var text: String? = null

    private var price: BigDecimal? = null

    fun setTitle(title: String) {
        this.title = title
    }

    fun updateText(text: String) {
        this.text = text
    }

    fun updatePrice(price: BigDecimal) {
        this.price = price
    }
}

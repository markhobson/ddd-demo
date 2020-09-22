package marketplace.domain

class ClassifiedAd(val id: ClassifiedAdId, val ownerId: UserId) {
    var title: ClassifiedAdTitle? = null
        private set

    var text: ClassifiedAdText? = null
        private set

    var price: Price? = null
        private set

    fun setTitle(title: ClassifiedAdTitle) {
        this.title = title
    }

    fun updateText(text: ClassifiedAdText) {
        this.text = text
    }

    fun updatePrice(price: Price) {
        this.price = price
    }
}

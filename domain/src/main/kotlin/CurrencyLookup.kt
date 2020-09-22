package marketplace.domain

interface CurrencyLookup {
    fun findCurrency(currencyCode: String): CurrencyDetails
}

data class CurrencyDetails(val currencyCode: String, val decimalPlaces: Int, val inUse: Boolean) {
    companion object {
        val NONE = CurrencyDetails("", 0, false)
    }
}

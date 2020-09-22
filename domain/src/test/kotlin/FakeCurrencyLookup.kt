package marketplace.domain

class FakeCurrencyLookup : CurrencyLookup {
    private val currencies = listOf(
        CurrencyDetails(currencyCode = "EUR", decimalPlaces = 2, inUse = true),
        CurrencyDetails(currencyCode = "USD", decimalPlaces = 2, inUse = true),
        CurrencyDetails(currencyCode = "JPY", decimalPlaces = 0, inUse = true),
        CurrencyDetails(currencyCode = "DEM", decimalPlaces = 2, inUse = false)
    )

    override fun findCurrency(currencyCode: String): CurrencyDetails {
        val currency = currencies.find { it.currencyCode == currencyCode }
        return currency ?: CurrencyDetails.NONE
    }
}
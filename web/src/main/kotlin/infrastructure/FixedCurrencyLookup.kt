package marketplace.web.infrastructure

import marketplace.domain.CurrencyDetails
import marketplace.domain.CurrencyLookup
import org.springframework.stereotype.Service

@Service
class FixedCurrencyLookup : CurrencyLookup {
    private val currencies = listOf(
        CurrencyDetails(currencyCode = "EUR", decimalPlaces = 2, inUse = true),
        CurrencyDetails(currencyCode = "USD", decimalPlaces = 2, inUse = true)
    )

    override fun findCurrency(currencyCode: String): CurrencyDetails =
        currencies.find { it.currencyCode == currencyCode }
            ?: CurrencyDetails.NONE
}

package marketplace.domain

import java.lang.IllegalArgumentException
import java.math.BigDecimal

// Uses composition instead of inheritance since Kotlin data classes are closed
data class Price private constructor(val money: Money) {
    init {
        if (money.amount < BigDecimal.ZERO) {
            throw IllegalArgumentException("Price cannot be negative")
        }
    }

    private constructor(amount: Double, currencyCode: String, currencyLookup: CurrencyLookup)
        : this(Money.fromDouble(amount, currencyCode, currencyLookup))

    private constructor(amount: BigDecimal, currencyCode: String)
        : this(Money(amount, CurrencyDetails(currencyCode, 0, false)))

    companion object {
        fun fromDouble(amount: Double, currency: String, currencyLookup: CurrencyLookup) =
            Price(amount, currency, currencyLookup)

        fun deserialize(amount: BigDecimal, currencyCode: String) =
            Price(amount, currencyCode)
    }
}

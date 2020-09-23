package marketplace.domain

import java.lang.Exception
import java.lang.IllegalArgumentException
import java.math.BigDecimal

data class Money internal constructor(val amount: BigDecimal, val currency: CurrencyDetails) {
    internal constructor(amount: BigDecimal, currencyCode: String, currencyLookup: CurrencyLookup)
        : this(amount, currencyLookup.findCurrency(currencyCode)) {
        if (currencyCode.isEmpty()) {
            throw IllegalArgumentException("Currency code must be specified")
        }

        if (amount.scale() > currency.decimalPlaces) {
            throw IllegalArgumentException("Amount in $currencyCode cannot have more than ${currency.decimalPlaces} "
                + "decimals")
        }

        if (!currency.inUse) {
            throw IllegalArgumentException("Currency $currencyCode is not valid")
        }
    }

    operator fun plus(summand: Money): Money {
        if (currency != summand.currency) {
            throw CurrencyMismatchException("Cannot sum amounts with different currencies")
        }

        return Money(amount + summand.amount, currency)
    }

    operator fun minus(subtrahend: Money): Money {
        if (currency != subtrahend.currency) {
            throw CurrencyMismatchException("Cannot subtract amounts with different currencies")
        }

        return Money(amount - subtrahend.amount, currency)
    }

    override fun toString() = "${currency.currencyCode} $amount"

    companion object {
        private const val DEFAULT_CURRENCY = "EUR"

        fun fromDouble(amount: Double, currency: String = DEFAULT_CURRENCY, currencyLookup: CurrencyLookup)
            = Money(BigDecimal.valueOf(amount), currency, currencyLookup)

        fun fromString(amount: String, currency: String = DEFAULT_CURRENCY, currencyLookup: CurrencyLookup)
            = Money(BigDecimal(amount), currency, currencyLookup)
    }
}

class CurrencyMismatchException(message: String) : Exception(message)

package marketplace.domain

import java.lang.Exception
import java.lang.IllegalArgumentException
import java.math.BigDecimal

data class Money(val amount: BigDecimal, val currency: CurrencyDetails) {
    init {
        if (amount.scale() > currency.decimalPlaces) {
            throw IllegalArgumentException("Amount in ${currency.currencyCode} cannot have more than "
                + "${currency.decimalPlaces} decimals")
        }

        if (!currency.inUse) {
            throw IllegalArgumentException("Currency ${currency.currencyCode} is not valid")
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
            = from(BigDecimal.valueOf(amount), currency, currencyLookup)

        fun fromString(amount: String, currency: String = DEFAULT_CURRENCY, currencyLookup: CurrencyLookup)
            = from(BigDecimal(amount), currency, currencyLookup)

        private fun from(amount: BigDecimal, currencyCode: String, currencyLookup: CurrencyLookup): Money {
            if (currencyCode.isEmpty()) {
                throw IllegalArgumentException("Currency code must be specified")
            }

            val currency = currencyLookup.findCurrency(currencyCode)

            return Money(amount, currency)
        }
    }
}

class CurrencyMismatchException(message: String) : Exception(message)

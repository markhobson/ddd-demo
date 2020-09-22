package marketplace.domain

import java.lang.IllegalArgumentException
import java.math.BigDecimal

/**
 * Uses composition instead of inheritance since Kotlin data classes are closed.
 */
data class Price(val money: Money) {
    init {
        if (money.amount < BigDecimal.ZERO) {
            throw IllegalArgumentException("Price cannot be negative")
        }
    }
}

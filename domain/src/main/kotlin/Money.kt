package marketplace.domain

import java.math.BigDecimal

data class Money(val amount: BigDecimal) {
    operator fun plus(summand: Money) = Money(amount + summand.amount)

    operator fun minus(subtrahend: Money) = Money(amount - subtrahend.amount)
}

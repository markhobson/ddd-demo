package marketplace.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MoneyTest {
    @Test
    fun `sum of money gives full amount`() {
        val coin1 = Money(BigDecimal(1))
        val coin2 = Money(BigDecimal(2))
        val coin3 = Money(BigDecimal(2))
        val banknote = Money(BigDecimal(5))

        assertEquals(banknote, coin1 + coin2 + coin3)
    }
}

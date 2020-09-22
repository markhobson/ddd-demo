package marketplace.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class MoneyTest {
    private val currencyLookup: CurrencyLookup = FakeCurrencyLookup()

    @Test
    fun `two of same amount should be equal`() {
        val firstAmount = Money.fromDouble(5.0, "EUR", currencyLookup)
        val secondAmount = Money.fromDouble(5.0, "EUR", currencyLookup)

        assertEquals(firstAmount, secondAmount)
    }

    @Test
    fun `two of same amount but different currencies should not be equal`() {
        val firstAmount = Money.fromDouble(5.0, "EUR", currencyLookup)
        val secondAmount = Money.fromDouble(5.0, "USD", currencyLookup)

        assertNotEquals(firstAmount, secondAmount)
    }

    @Test
    fun `from string and from big decimal should be equal`() {
        // Use 5.01 rather than 5.00 as Kotlin cannot represent that as a double
        val firstAmount = Money.fromDouble(5.01, "EUR", currencyLookup)
        val secondAmount = Money.fromString("5.01", "EUR", currencyLookup)

        assertEquals(firstAmount, secondAmount)
    }

    @Test
    fun `sum of money gives full amount`() {
        val coin1 = Money.fromDouble(1.0, "EUR", currencyLookup)
        val coin2 = Money.fromDouble(2.0, "EUR", currencyLookup)
        val coin3 = Money.fromDouble(2.0, "EUR", currencyLookup)
        val banknote = Money.fromDouble(5.0, "EUR", currencyLookup)

        assertEquals(banknote, coin1 + coin2 + coin3)
    }

    @Test
    fun `unused currency should not be allowed`() {
        assertThrows(IllegalArgumentException::class.java) {
            Money.fromDouble(100.0, "DEM", currencyLookup)
        }
    }

    @Test
    fun `unknown currency should not be allowed`() {
        assertThrows(IllegalArgumentException::class.java) {
            Money.fromDouble(100.0, "WHAT?", currencyLookup)
        }
    }

    @Test
    fun `throw when too many decimal places`() {
        assertThrows(IllegalArgumentException::class.java) {
            Money.fromDouble(100.123, "EUR", currencyLookup)
        }
    }

    @Test
    fun `throws on adding different currencies`() {
        val firstAmount = Money.fromDouble(5.0, "USD", currencyLookup)
        val secondAmount = Money.fromDouble(5.0, "EUR", currencyLookup)

        assertThrows(CurrencyMismatchException::class.java) {
            firstAmount + secondAmount
        }
    }

    @Test
    fun `throws on subtracting different currencies`() {
        val firstAmount = Money.fromDouble(5.0, "USD", currencyLookup)
        val secondAmount = Money.fromDouble(5.0, "EUR", currencyLookup)

        assertThrows(CurrencyMismatchException::class.java) {
            firstAmount - secondAmount
        }
    }
}

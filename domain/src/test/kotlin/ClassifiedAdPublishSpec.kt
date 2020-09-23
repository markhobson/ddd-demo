package marketplace.domain

import marketplace.domain.ClassifiedAd.ClassifiedAdState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.UUID

class ClassifiedAdPublishSpec {
    private val classifiedAd: ClassifiedAd = ClassifiedAd(
        ClassifiedAdId(UUID.randomUUID()),
        UserId(UUID.randomUUID())
    )

    @Test
    fun `can publish a valid ad`() {
        classifiedAd.setTitle(ClassifiedAdTitle.fromString("Test ad"))
        classifiedAd.updateText(ClassifiedAdText.fromString("Please buy my stuff"))
        classifiedAd.updatePrice(Price.fromDouble(100.10, "EUR", FakeCurrencyLookup()))
        classifiedAd.requestToPublish()

        assertEquals(ClassifiedAdState.PENDING_REVIEW, classifiedAd.state)
    }

    @Test
    fun `cannot publish without title`() {
        classifiedAd.updateText(ClassifiedAdText.fromString("Please buy my stuff"))
        classifiedAd.updatePrice(Price.fromDouble(100.10, "EUR", FakeCurrencyLookup()))

        assertThrows(InvalidEntityStateException::class.java) {
            classifiedAd.requestToPublish()
        }
    }

    @Test
    fun `cannot publish without text`() {
        classifiedAd.setTitle(ClassifiedAdTitle.fromString("Test ad"))
        classifiedAd.updatePrice(Price.fromDouble(100.10, "EUR", FakeCurrencyLookup()))

        assertThrows(InvalidEntityStateException::class.java) {
            classifiedAd.requestToPublish()
        }
    }

    @Test
    fun `cannot publish without price`() {
        classifiedAd.setTitle(ClassifiedAdTitle.fromString("Test ad"))
        classifiedAd.updateText(ClassifiedAdText.fromString("Please buy my stuff"))

        assertThrows(InvalidEntityStateException::class.java) {
            classifiedAd.requestToPublish()
        }
    }

    @Test
    fun `cannot publish with zero price`() {
        classifiedAd.setTitle(ClassifiedAdTitle.fromString("Test ad"))
        classifiedAd.updateText(ClassifiedAdText.fromString("Please buy my stuff"))
        classifiedAd.updatePrice(Price.fromDouble(0.0, "EUR", FakeCurrencyLookup()))

        assertThrows(InvalidEntityStateException::class.java) {
            classifiedAd.requestToPublish()
        }
    }
}

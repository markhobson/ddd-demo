package marketplace.web.infrastructure

import marketplace.domain.ClassifiedAd
import marketplace.domain.ClassifiedAd.ClassifiedAdState
import marketplace.domain.ClassifiedAdId
import marketplace.domain.ClassifiedAdText
import marketplace.domain.ClassifiedAdTitle
import marketplace.domain.Price
import marketplace.domain.UserId
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.util.UUID

@Table("CLASSIFIED_AD")
data class ClassifiedAdEntity(
    @Id
    private val id: UUID,
    private val ownerId: UUID,
    private val title: String?,
    private val text: String?,
    private val amount: BigDecimal?,
    private val currencyCode: String?,
    private val state: ClassifiedAdState,
    private val approvedBy: UUID?
) {
    private constructor(entity: ClassifiedAd) : this(
        id = entity.id.value,
        ownerId = entity.ownerId.value,
        title = entity.title?.value,
        text = entity.text?.value,
        amount = entity.price?.let { it.money.amount },
        currencyCode = entity.price?.let { it.money.currency.currencyCode },
        state = entity.state,
        approvedBy = entity.approvedBy?.value
    )

    fun toDomain(): ClassifiedAd =
        ClassifiedAd.deserialize(
            id = ClassifiedAdId(id),
            ownerId = UserId(ownerId),
            title = title?.let { ClassifiedAdTitle.fromString(it) },
            text = text?.let { ClassifiedAdText.fromString(it) },
            price = if (amount != null && currencyCode != null) Price.deserialize(amount, currencyCode) else null,
            state = state,
            approvedBy = approvedBy?.let { UserId(it) }
        )

    companion object {
        fun fromDomain(entity: ClassifiedAd): ClassifiedAdEntity =
            ClassifiedAdEntity(entity)
    }
}

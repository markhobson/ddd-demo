package marketplace.web.infrastructure

import marketplace.domain.classifiedAd.ClassifiedAd
import marketplace.domain.classifiedAd.ClassifiedAd.ClassifiedAdState
import marketplace.domain.classifiedAd.ClassifiedAdId
import marketplace.domain.classifiedAd.ClassifiedAdText
import marketplace.domain.classifiedAd.ClassifiedAdTitle
import marketplace.domain.classifiedAd.Price
import marketplace.domain.UserId
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.MappedCollection
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
    private val approvedBy: UUID?,
    @MappedCollection(idColumn = "CLASSIFIED_AD_ID")
    private val pictures: Set<PictureEntity>
) {
    private constructor(entity: ClassifiedAd) : this(
        id = entity.id.value,
        ownerId = entity.ownerId.value,
        title = entity.title?.value,
        text = entity.text?.value,
        amount = entity.price?.let { it.money.amount },
        currencyCode = entity.price?.let { it.money.currency.currencyCode },
        state = entity.state,
        approvedBy = entity.approvedBy?.value,
        pictures = entity.pictures.map(PictureEntity::fromDomain).toSet()
    )

    fun toDomain(): ClassifiedAd =
        ClassifiedAd.deserialize(
            id = ClassifiedAdId(id),
            ownerId = UserId(ownerId),
            title = title?.let(ClassifiedAdTitle::fromString),
            text = text?.let(ClassifiedAdText::fromString),
            price = if (amount != null && currencyCode != null) Price.deserialize(amount, currencyCode) else null,
            state = state,
            approvedBy = approvedBy?.let(::UserId),
            pictures = pictures.map(PictureEntity::toDomain)
        )

    companion object {
        fun fromDomain(entity: ClassifiedAd): ClassifiedAdEntity =
            ClassifiedAdEntity(entity)
    }
}

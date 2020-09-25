package marketplace.web.infrastructure

import marketplace.domain.classifiedAd.ClassifiedAd
import marketplace.domain.classifiedAd.ClassifiedAdId
import marketplace.domain.classifiedAd.ClassifiedAdRepository
import org.springframework.stereotype.Repository

@Repository
class ClassifiedAdRepositoryAdapter(private val delegate: SpringClassifiedAdRepository)
    : ClassifiedAdRepository {
    override fun findById(id: ClassifiedAdId): ClassifiedAd? =
        delegate.findById(id.value)
            .map { it.toDomain() }
            .orElse(null)

    override fun insert(entity: ClassifiedAd): ClassifiedAd =
        delegate.insert(ClassifiedAdEntity.fromDomain(entity))
            .toDomain()

    override fun update(entity: ClassifiedAd): ClassifiedAd =
        delegate.update(ClassifiedAdEntity.fromDomain(entity))
            .toDomain()

    override fun existsById(id: ClassifiedAdId): Boolean =
        delegate.existsById(id.value)
}

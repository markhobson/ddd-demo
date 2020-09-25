package marketplace.domain.classifiedAd

interface ClassifiedAdRepository {
    fun findById(id: ClassifiedAdId): ClassifiedAd?

    fun insert(entity: ClassifiedAd): ClassifiedAd

    fun update(entity: ClassifiedAd): ClassifiedAd

    fun existsById(id: ClassifiedAdId): Boolean
}

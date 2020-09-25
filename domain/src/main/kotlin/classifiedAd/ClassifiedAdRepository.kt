package marketplace.domain.classifiedAd

interface ClassifiedAdRepository {
    fun load(id: ClassifiedAdId): ClassifiedAd?

    fun add(entity: ClassifiedAd): ClassifiedAd

    fun update(entity: ClassifiedAd): ClassifiedAd

    fun exists(id: ClassifiedAdId): Boolean
}

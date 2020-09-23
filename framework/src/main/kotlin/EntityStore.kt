package marketplace.framework

interface EntityStore {
    fun <T : Entity> load(entityId: String): T?

    fun <T : Entity> save(entity: T)

    fun <T : Entity> exists(entityId: String): Boolean
}

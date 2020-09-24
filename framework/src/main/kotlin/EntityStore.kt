package marketplace.framework

interface EntityStore {
    fun <T> load(entityId: String): T?

    fun <T> save(entity: T)

    fun <T> exists(entityId: String): Boolean
}

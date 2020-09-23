package marketplace.framework

abstract class Entity {
    private val events: MutableList<Any> = mutableListOf()

    protected fun apply(event: Any) {
        on(event)
        ensureValidState()
        events.add(event)
    }

    // Renamed from 'when' due to clash with Kotlin keyword
    protected abstract fun on(event: Any)

    fun getChanges() = events.asIterable()

    fun clearChanges() {
        events.clear()
    }

    protected abstract fun ensureValidState()
}

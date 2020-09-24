package marketplace.framework

abstract class AggregateRoot<T>(id: T) {
    var id = id
        protected set

    private val changes: MutableList<Any> = mutableListOf()

    protected fun apply(event: Any) {
        on(event)
        ensureValidState()
        changes.add(event)
    }

    // Renamed from 'when' due to clash with Kotlin keyword
    protected abstract fun on(event: Any)

    fun clearChanges() {
        changes.clear()
    }

    protected abstract fun ensureValidState()
}

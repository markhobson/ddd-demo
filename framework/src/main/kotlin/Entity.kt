package marketplace.framework

abstract class Entity {
    private val events: MutableList<Any> = mutableListOf()

    protected fun raise(event: Any) {
        events.add(event)
    }

    fun getChanges() = events.asIterable()

    fun clearChanges() {
        events.clear()
    }
}

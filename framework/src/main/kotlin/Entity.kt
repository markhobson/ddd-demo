package marketplace.framework

abstract class Entity<T>(id: T, val applier: (Any) -> Unit) : InternalEventHandler {
    var id = id
        protected set

    protected fun apply(event: Any) {
        on(event)
        applier(event)
    }

    // Renamed from 'when' due to clash with Kotlin keyword
    protected abstract fun on(event: Any)

    override fun handle(event: Any) {
        on(event)
    }
}

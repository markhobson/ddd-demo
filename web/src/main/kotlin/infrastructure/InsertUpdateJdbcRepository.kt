package marketplace.web.infrastructure

/**
 * Spring Data repository customiser to expose explicit insert and update operations.
 *
 * Standard `CrudRepository`s only provide a `save` operation which is problematic when clients, rather than the
 * database, are responsible for generating ids. When an id is set, Spring Data JDBC will always try to update it even
 * if it does not exist.
 *
 * Implementing this interface exposes the internal separate `insert` and `update` operations that `save` delegates to.
 *
 * See: [Custom Implementations for Spring Data Repositories](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#repositories.custom-implementations)
 */
interface InsertUpdateJdbcRepository<T> {
    fun <S : T> insert(instance: S): S

    fun <S : T> update(instance: S): S
}

package marketplace.web.infrastructure

import org.springframework.data.jdbc.core.JdbcAggregateOperations
import org.springframework.transaction.annotation.Transactional

open class InsertUpdateJdbcRepositoryImpl<T>(private val entityOperations: JdbcAggregateOperations)
    : InsertUpdateJdbcRepository<T> {
    @Transactional
    override fun <S : T> insert(instance: S): S =
        entityOperations.insert(instance)

    @Transactional
    override fun <S : T> update(instance: S): S =
        entityOperations.update(instance)
}

package marketplace.web.infrastructure

import marketplace.framework.UnitOfWork
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager

@Service
class SpringUnitOfWork(private val transactionManager: PlatformTransactionManager) : UnitOfWork {
    override fun commit() {
        val transaction = transactionManager.getTransaction(null)
        transactionManager.commit(transaction)
    }
}

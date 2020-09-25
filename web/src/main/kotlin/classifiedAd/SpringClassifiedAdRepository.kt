package marketplace.web.classifiedAd

import marketplace.web.infrastructure.InsertUpdateJdbcRepository
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface SpringClassifiedAdRepository
    : CrudRepository<ClassifiedAdEntity, UUID>, InsertUpdateJdbcRepository<ClassifiedAdEntity>

package marketplace.web.infrastructure

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface SpringClassifiedAdRepository
    : CrudRepository<ClassifiedAdEntity, UUID>, InsertUpdateJdbcRepository<ClassifiedAdEntity>

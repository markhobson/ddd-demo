package marketplace.web.userProfile

import marketplace.web.infrastructure.InsertUpdateJdbcRepository
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface SpringUserProfileRepository
    : CrudRepository<UserProfileEntity, UUID>, InsertUpdateJdbcRepository<UserProfileEntity>

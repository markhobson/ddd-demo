package marketplace.web.userProfile

import marketplace.domain.UserId
import marketplace.domain.userProfile.UserProfile
import marketplace.domain.userProfile.UserProfileRepository
import org.springframework.stereotype.Repository

@Repository
class UserProfileRepositoryAdapter(private val delegate: SpringUserProfileRepository) : UserProfileRepository {
    override fun load(id: UserId): UserProfile? =
        delegate.findById(id.value)
            .map { it.toDomain() }
            .orElse(null)

    override fun add(entity: UserProfile) =
        delegate.insert(UserProfileEntity.fromDomain(entity))
            .toDomain()

    override fun update(entity: UserProfile) =
        delegate.update(UserProfileEntity.fromDomain(entity))
            .toDomain()

    override fun exists(id: UserId): Boolean =
        delegate.existsById(id.value)
}

package marketplace.domain.userProfile

import marketplace.domain.UserId

interface UserProfileRepository {
    fun load(id: UserId): UserProfile?

    fun add(entity: UserProfile): UserProfile

    fun update(entity: UserProfile): UserProfile

    fun exists(id: UserId): Boolean
}

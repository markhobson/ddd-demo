package marketplace.domain.userProfile

import java.util.UUID

object Events {
    data class UserRegistered(val userId: UUID, val fullName: String, val displayName: String)

    data class ProfilePhotoUploaded(val userId: UUID, val photoUrl: String)

    data class UserFullNameUpdated(val userId: UUID, val fullName: String)

    data class UserDisplayNameUpdated(val userId: UUID, val displayName: String)
}

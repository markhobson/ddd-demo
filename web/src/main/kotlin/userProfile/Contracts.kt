package marketplace.web.userProfile

import java.util.UUID

object Contracts {
    object V1 {
        data class RegisterUser(val userId: UUID, val fullName: String, val displayName: String)

        data class UpdateUserFullName(val userId: UUID, val fullName: String)

        data class UpdateUserDisplayName(val userId: UUID, val displayName: String)

        data class UpdateUserProfilePhoto(val userId: UUID, val photoUrl: String)
    }
}

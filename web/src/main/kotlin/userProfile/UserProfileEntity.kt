package marketplace.web.userProfile

import marketplace.domain.UserId
import marketplace.domain.userProfile.DisplayName
import marketplace.domain.userProfile.FullName
import marketplace.domain.userProfile.UserProfile
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("USER_PROFILE")
data class UserProfileEntity(
    @Id
    private val id: UUID,
    private val fullName: String,
    private val displayName: String,
    private val photoUrl: String?
) {
    private constructor(entity: UserProfile) : this(
        id = entity.id.value,
        fullName = entity.fullName.value,
        displayName = entity.displayName.value,
        photoUrl = entity.photoUrl
    )

    fun toDomain(): UserProfile =
        UserProfile.deserialize(
            id = UserId(id),
            fullName = FullName.deserialize(fullName),
            displayName = DisplayName.deserialize(displayName),
            photoUrl = photoUrl
        )

    companion object {
        fun fromDomain(entity: UserProfile): UserProfileEntity =
            UserProfileEntity(entity)
    }
}

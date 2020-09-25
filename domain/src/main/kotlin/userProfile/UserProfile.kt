package marketplace.domain.userProfile

import marketplace.domain.UserId
import marketplace.framework.AggregateRoot
import java.net.URI

class UserProfile(id: UserId, fullName: FullName, displayName: DisplayName) : AggregateRoot<UserId>(id) {
    var fullName = fullName
        private set

    var displayName = displayName
        private set

    var photoUrl: String? = null
        private set

    init {
        apply(Events.UserRegistered(id.value, fullName.value, displayName.value))
    }

    fun updateFullName(fullName: FullName) {
        apply(Events.UserFullNameUpdated(id.value, fullName.value))
    }

    fun updateDisplayName(displayName: DisplayName) {
        apply(Events.UserDisplayNameUpdated(id.value, displayName.value))
    }

    fun updateProfilePhoto(photoUrl: URI) {
        apply(Events.ProfilePhotoUploaded(id.value, photoUrl.toString()))
    }

    override fun on(event: Any) {
        when (event) {
            is Events.UserRegistered -> {
                id = UserId(event.userId)
                fullName = FullName(event.fullName)
                displayName = DisplayName(event.displayName)
            }
            is Events.UserFullNameUpdated -> {
                fullName = FullName(event.fullName)
            }
            is Events.UserDisplayNameUpdated -> {
                displayName = DisplayName(event.displayName)
            }
            is Events.ProfilePhotoUploaded -> {
                photoUrl = event.photoUrl
            }
        }
    }

    override fun ensureValidState() {
    }

    companion object {
        fun deserialize(
            id: UserId,
            fullName: FullName,
            displayName: DisplayName,
            photoUrl: String?
        ): UserProfile =
            UserProfile(id, fullName, displayName).also {
                it.photoUrl = photoUrl
            }
    }
}

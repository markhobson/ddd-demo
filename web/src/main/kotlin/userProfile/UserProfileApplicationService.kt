package marketplace.web.userProfile

import marketplace.domain.ContentModeration
import marketplace.domain.UserId
import marketplace.domain.userProfile.DisplayName
import marketplace.domain.userProfile.FullName
import marketplace.domain.userProfile.UserProfile
import marketplace.domain.userProfile.UserProfileRepository
import marketplace.framework.ApplicationService
import marketplace.framework.UnitOfWork
import marketplace.web.userProfile.Contracts.V1
import org.springframework.stereotype.Service
import java.net.URI
import java.util.UUID

@Service
class UserProfileApplicationService(
    private val repository: UserProfileRepository,
    private val unitOfWork: UnitOfWork,
    private val contentModeration: ContentModeration
) : ApplicationService {
    override fun handle(command: Any) {
        when (command) {
            is V1.RegisterUser -> {
                if (repository.exists(UserId(command.userId))) {
                    throw IllegalStateException("Entity with id ${command.userId} already exists")
                }
                val userProfile = UserProfile(
                    UserId(command.userId),
                    FullName.fromString(command.fullName),
                    DisplayName.fromString(command.displayName, contentModeration)
                )
                repository.add(userProfile)
                unitOfWork.commit()
            }

            is V1.UpdateUserFullName -> handleUpdate(command.userId) {
                profile -> profile.updateFullName(FullName.fromString(command.fullName))
            }

            is V1.UpdateUserDisplayName -> handleUpdate(command.userId) {
                profile -> profile.updateDisplayName(DisplayName.fromString(command.displayName, contentModeration))
            }

            is V1.UpdateUserProfilePhoto -> handleUpdate(command.userId) {
                profile -> profile.updateProfilePhoto(URI.create(command.photoUrl))
            }

            else -> throw IllegalArgumentException("Command type ${command.javaClass.name} is unknown")
        }
    }

    private fun handleUpdate(userProfileId: UUID, operation: (UserProfile) -> Unit) {
        val userProfile = repository.load(UserId(userProfileId))
            ?: throw IllegalArgumentException("Entity with id $userProfileId cannot be found")

        operation(userProfile)

        unitOfWork.commit()
    }
}

package marketplace.domain.userProfile

import marketplace.domain.ContentModeration
import marketplace.domain.DomainExceptions

data class DisplayName internal constructor(val value: String) {
    companion object {
        fun fromString(displayName: String, hasProfanity: ContentModeration): DisplayName {
            if (displayName.isEmpty()) {
                throw IllegalAccessException("Display name not specified")
            }
            if (hasProfanity.checkTextForProfanity(displayName)) {
                throw DomainExceptions.ProfanityFound(displayName)
            }

            return DisplayName(displayName)
        }

        fun deserialize(value: String) = DisplayName(value)
    }
}

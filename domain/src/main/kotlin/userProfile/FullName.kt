package marketplace.domain.userProfile

data class FullName internal constructor(val value: String) {
    companion object {
        fun fromString(fullName: String): FullName {
            if (fullName.isEmpty()) {
                throw IllegalArgumentException("Full name not specified")
            }
            return FullName(fullName)
        }

        fun deserialize(value: String) = FullName(value)
    }
}

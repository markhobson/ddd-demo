package marketplace.domain

import java.lang.IllegalArgumentException

data class ClassifiedAdTitle internal constructor(val value: String) {
    companion object {
        fun fromString(title: String): ClassifiedAdTitle {
            checkValidity(title)
            return ClassifiedAdTitle(title)
        }

        private fun checkValidity(value: String) {
            if (value.length > 100) {
                throw IllegalArgumentException("Title cannot be longer than 100 characters")
            }
        }
    }
}

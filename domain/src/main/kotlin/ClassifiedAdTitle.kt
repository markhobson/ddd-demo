package marketplace.domain

import java.lang.IllegalArgumentException

// Data class constructor cannot be private
data class ClassifiedAdTitle(val value: String) {
    init {
        if (value.length > 100) {
            throw IllegalArgumentException("Title cannot be longer than 100 characters")
        }
    }

    companion object {
        fun fromString(title: String): ClassifiedAdTitle = ClassifiedAdTitle(title)
    }
}

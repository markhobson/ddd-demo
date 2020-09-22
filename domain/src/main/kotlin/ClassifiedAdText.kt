package marketplace.domain

// Data class constructor cannot be private
data class ClassifiedAdText(val value: String) {
    companion object {
        fun fromString(text: String): ClassifiedAdText = ClassifiedAdText(text)
    }
}

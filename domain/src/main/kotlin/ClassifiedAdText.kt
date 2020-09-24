package marketplace.domain

data class ClassifiedAdText internal constructor(val value: String) {
    companion object {
        fun fromString(text: String): ClassifiedAdText =
            ClassifiedAdText(text)
    }
}

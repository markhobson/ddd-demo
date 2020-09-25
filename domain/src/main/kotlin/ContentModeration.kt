package marketplace.domain

interface ContentModeration {
    fun checkTextForProfanity(text: String): Boolean
}

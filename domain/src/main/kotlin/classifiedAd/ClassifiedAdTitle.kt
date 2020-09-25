package marketplace.domain.classifiedAd

import java.lang.IllegalArgumentException

data class ClassifiedAdTitle internal constructor(val value: String) {
    companion object {
        fun fromString(title: String): ClassifiedAdTitle {
            checkValidity(title)
            return ClassifiedAdTitle(title)
        }

        fun fromHtml(htmlTitle: String): ClassifiedAdTitle {
            val supportedTagsReplaced = htmlTitle
                .replace("<i>", "*")
                .replace("</i>", "*")
                .replace("<b>", "*")
                .replace("</b>", "**")

            val value = supportedTagsReplaced.replace(Regex("<.*?>"), "")
            checkValidity(value)

            return ClassifiedAdTitle(value)
        }

        private fun checkValidity(value: String) {
            if (value.length > 100) {
                throw IllegalArgumentException("Title cannot be longer than 100 characters")
            }
        }
    }
}

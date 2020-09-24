package marketplace.domain

import marketplace.framework.Entity
import java.lang.IllegalArgumentException
import java.net.URI
import java.util.UUID

class Picture(applier: (Any) -> Unit) : Entity<PictureId>(PictureId(), applier) {
    var size: PictureSize = PictureSize(0, 0)
        private set

    var location: URI = URI.create("http://localhost")
        private set

    var order: Int = 0
        private set

    override fun on(event: Any) {
        when (event) {
            is Events.PictureAddedToAClassifiedAd -> {
                id = PictureId(event.pictureId)
                size = PictureSize.internal(event.height, event.width)
                location = URI.create(event.url)
                order = event.order
            }
            is Events.ClassifiedAdPictureResized -> {
                size = PictureSize.internal(event.height, event.width)
            }
        }
    }

    fun resize(newSize: PictureSize) {
        apply(Events.ClassifiedAdPictureResized(
            pictureId = id.value,
            height = newSize.height,
            width = newSize.width
        ))
    }
}

data class PictureId(val value: UUID) {
    constructor() : this(UUID.randomUUID())
}

data class PictureSize internal constructor(val width: Int, val height: Int, val internal: Unit) {
    constructor(width: Int, height: Int) : this(width, height, Unit) {
        if (width <= 0) {
            throw IllegalArgumentException("Picture width must be a positive number")
        }

        if (height <= 0) {
            throw IllegalArgumentException("Picture height must be a positive number")
        }
    }

    companion object {
        fun internal(width: Int, height: Int): PictureSize =
            PictureSize(width, height, Unit)
    }
}

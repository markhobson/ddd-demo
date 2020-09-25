package marketplace.domain

import marketplace.framework.Entity
import marketplace.framework.Ids
import java.lang.IllegalArgumentException
import java.net.URI
import java.util.UUID

class Picture(applier: (Any) -> Unit) : Entity<PictureId>(PictureId(), applier) {
    var size: PictureSize = PictureSize.deserialize(0, 0)
        private set

    var location: URI = URI.create("http://localhost")
        private set

    var order: Int = 0
        private set

    override fun on(event: Any) {
        when (event) {
            is Events.PictureAddedToAClassifiedAd -> {
                id = PictureId(event.pictureId)
                size = PictureSize.deserialize(event.height, event.width)
                location = URI.create(event.url)
                order = event.order
            }
            is Events.ClassifiedAdPictureResized -> {
                size = PictureSize.deserialize(event.height, event.width)
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

    companion object {
        fun deserialize(
            id: PictureId,
            size: PictureSize,
            location: URI,
            order: Int
        ) =
            Picture { Unit }.also {
                it.id = id
                it.size = size
                it.location = location
                it.order = order
            }
    }
}

data class PictureId(val value: UUID) {
    constructor() : this(Ids.ZERO)
}

data class PictureSize private constructor(val width: Int, val height: Int, val primary: Unit) {
    constructor(width: Int, height: Int) : this(width, height, Unit) {
        if (width <= 0) {
            throw IllegalArgumentException("Picture width must be a positive number")
        }

        if (height <= 0) {
            throw IllegalArgumentException("Picture height must be a positive number")
        }
    }

    companion object {
        fun deserialize(width: Int, height: Int): PictureSize =
            PictureSize(width, height, Unit)
    }
}

package marketplace.web.infrastructure

import marketplace.domain.Picture
import marketplace.domain.PictureId
import marketplace.domain.PictureSize
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.net.URI
import java.util.UUID

@Table("PICTURE")
data class PictureEntity(
    @Id
    private val id: UUID,
    private val height: Int,
    private val width: Int,
    private val location: String,
    private val order: Int
) {
    fun toDomain() =
        Picture.deserialize(
            id = PictureId(id),
            size = PictureSize.deserialize(width, height),
            location = URI.create(location),
            order = order
        )

    companion object {
        fun fromDomain(entity: Picture): PictureEntity =
            PictureEntity(
                entity.id.value,
                entity.size.height,
                entity.size.width,
                entity.location.toString(),
                entity.order
            )
    }
}

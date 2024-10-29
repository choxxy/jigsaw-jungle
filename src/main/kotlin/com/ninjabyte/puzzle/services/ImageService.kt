package com.ninjabyte.puzzle.services

import com.ninjabyte.puzzle.entities.Image
import com.ninjabyte.puzzle.services.dtos.ImageModel
import org.springframework.http.ResponseEntity
import java.util.UUID

interface ImageService {
    fun uploadImage(imageModel: ImageModel): String

    fun loadAll(): List<Image>

    fun load(photoName: String): Image?

    fun load(photoId: UUID): Image

    fun delete(photoId: UUID)

    fun deleteAll()
}
package com.ninjabyte.puzzle.services.impl

import com.ninjabyte.puzzle.entities.Image
import com.ninjabyte.puzzle.repositories.ImageRepository
import com.ninjabyte.puzzle.services.CloudinaryService
import com.ninjabyte.puzzle.services.ImageService
import com.ninjabyte.puzzle.services.dtos.ImageModel
import org.springframework.stereotype.Service
import java.util.*


@Service
class ImageServiceImpl(
    private val cloudinaryService: CloudinaryService,
    val imageRepository: ImageRepository
) : ImageService {

    override fun uploadImage(imageModel: ImageModel): String {

        val imageUrl = cloudinaryService.uploadFile(imageModel.file, "folder_1")

        if (imageUrl != null) {
            val image = Image(
                name = imageModel.name,
                url = imageUrl,
                playCount = 0,
                likes = 0
            )
            imageRepository.save<Image?>(image)

        } else {
            return ""
        }
        return imageUrl

    }

    override fun loadAll(): List<Image> {
        return imageRepository.findAll()
    }

    override fun load(photoName: String): Image? {
        return imageRepository.findImageByName(photoName)
    }

    override fun load(photoId: UUID): Image {
        return imageRepository.findById(photoId).get()
    }

    override fun delete(photoId: UUID) {
        imageRepository.deleteById(photoId)
    }

    override fun deleteAll() {
        imageRepository.deleteAll()
    }
}
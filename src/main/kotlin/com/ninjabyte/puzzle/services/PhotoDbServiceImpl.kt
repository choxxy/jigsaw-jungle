package com.ninjabyte.puzzle.services

import com.ninjabyte.puzzle.entities.Photo
import com.ninjabyte.puzzle.repositories.PhotoRepository
import org.springframework.stereotype.Service

@Service
class PhotoDbServiceImpl(
    private val photoRepository: PhotoRepository
) : PhotoDbService {
    override fun store(photo: Photo) = photoRepository.save(photo)

    override fun loadAll(): List<Photo> = photoRepository.findAll() as List<Photo>

    override fun load(photoName: String): Photo {
        val photoId = photoName.replace("[^0-9]", "").toLong()
        return photoRepository.findById(photoId).get()
    }

    override fun load(photoId: Long): Photo = photoRepository.findById(photoId).get()


    override fun delete(photoId: Long) = photoRepository.deleteById(photoId)


    override fun deleteAll() = photoRepository.deleteAll()

}
package com.ninjabyte.puzzle.integration.transformer

import com.ninjabyte.puzzle.entities.toPhoto
import com.ninjabyte.puzzle.repos.PhotoRepository
import org.springframework.stereotype.Service
import java.io.File

@Service
class PhotoFromFileHandler(private val photoRepository: PhotoRepository) {
    fun persist(file: File) {
        val photo = file.toPhoto()
        photoRepository.save(photo)

    }
}
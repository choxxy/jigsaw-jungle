package com.ninjabyte.puzzle.controllers

import com.ninjabyte.puzzle.entities.Image
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import com.ninjabyte.puzzle.repositories.ImageRepository
import java.util.UUID

@RestController
@RequestMapping("/api/photos")
class PhotoController(@Autowired private val imageRepository: ImageRepository) {

    @PutMapping("/{id}/like")
    fun likePhoto(@PathVariable id: UUID): ResponseEntity<Image> {
        val photo = imageRepository.findById(id).orElse(null)
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        photo.likes += 1
        imageRepository.save(photo)
        return ResponseEntity.ok(photo)
    }

    @PutMapping("/{id}/play")
    fun playPhoto(@PathVariable id: UUID): ResponseEntity<Image> {
        val photo = imageRepository.findById(id).orElse(null)
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        photo.playCount += 1
        imageRepository.save(photo)
        return ResponseEntity.ok(photo)
    }
}

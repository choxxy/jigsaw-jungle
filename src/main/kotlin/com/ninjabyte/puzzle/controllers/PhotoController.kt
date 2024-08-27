package com.ninjabyte.puzzle.controllers

import com.ninjabyte.puzzle.entities.Photo
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import com.ninjabyte.puzzle.repositories.PhotoRepository

@RestController
@RequestMapping("/api/photos")
class PhotoController(@Autowired private val photoRepository: PhotoRepository) {

    @PutMapping("/{id}/like")
    fun likePhoto(@PathVariable id: Long): ResponseEntity<Photo> {
        val photo = photoRepository.findById(id).orElse(null)
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        photo.likes += 1
        photoRepository.save(photo)
        return ResponseEntity.ok(photo)
    }

    @PutMapping("/{id}/play")
    fun playPhoto(@PathVariable id: Long): ResponseEntity<Photo> {
        val photo = photoRepository.findById(id).orElse(null)
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        photo.played += 1
        photoRepository.save(photo)
        return ResponseEntity.ok(photo)
    }
}

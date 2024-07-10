package com.ninjabyte.puzzle.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDateTime

@Entity
class Photo(
    var description: String = "",
    var originalFilename: String,
    var photoUrl: String = "",
    var likes: Int = 0,
    var played: Int = 0,
    var createdBy: String = "Admin",
    var createdOn: LocalDateTime = LocalDateTime.now(),
    @Id
    @GeneratedValue(generator = "seq-generator")
    var id: Long = 0
)

fun File.toPhoto() = Photo(originalFilename = this.name)

fun MultipartFile.toPhoto() = Photo(originalFilename = this.originalFilename!!)
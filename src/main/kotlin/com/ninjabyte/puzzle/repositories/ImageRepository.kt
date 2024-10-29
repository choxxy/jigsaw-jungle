package com.ninjabyte.puzzle.repositories

import com.ninjabyte.puzzle.entities.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ImageRepository : JpaRepository<Image, UUID> {
    fun findImageByName(string: String): Image?
}

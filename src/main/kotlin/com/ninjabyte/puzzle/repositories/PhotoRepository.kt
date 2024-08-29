package com.ninjabyte.puzzle.repositories

import com.ninjabyte.puzzle.entities.Photo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PhotoRepository : JpaRepository<Photo, Long>{
    fun findAllByOrderByCreatedOnDesc(): Iterable<Photo>
    fun findAllByOrderByPlayedDesc(): Iterable<Photo>
    fun findAllByOrderByLikesDesc(): Iterable<Photo>
}

package com.ninjabyte.puzzle.repos

import com.ninjabyte.puzzle.entities.Photo
import org.springframework.data.repository.CrudRepository

interface PhotoRepository : CrudRepository<Photo, Long> {
    fun findAllByOrderByCreatedOnDesc(): Iterable<Photo>
    fun findAllByOrderByPlayedDesc(): Iterable<Photo>
    fun findAllByOrderByLikesDesc(): Iterable<Photo>
}

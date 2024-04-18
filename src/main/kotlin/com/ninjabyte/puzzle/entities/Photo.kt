package com.ninjabyte.puzzle.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Photo(
    var photoUrl: String,
    var likes: String,
    var played: String,
    var createdOn: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue var id: Long? = null
)
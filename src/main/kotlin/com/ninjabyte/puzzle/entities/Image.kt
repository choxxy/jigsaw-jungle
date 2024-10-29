package com.ninjabyte.puzzle.entities

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "images")
class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    val id: UUID? = null,
    @Column(name = "name_image")
    val name: String,
    @Column(name = "url_image")
    val url: String,
    var likes: Int,
    var playCount: Int
) {
    override fun toString(): String = name
}
package com.ninjabyte.puzzle.services

import com.ninjabyte.puzzle.entities.Photo


interface PhotoDbService {

    fun store(photo: Photo): Photo

    fun loadAll(): List<Photo>

    fun load(photoName: String): Photo

    fun load(photoId: Long): Photo

    fun delete(photoId: Long)

    fun deleteAll()
}
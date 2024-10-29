package com.ninjabyte.puzzle.services.dtos

import org.springframework.web.multipart.MultipartFile

data class ImageModel(
    val name: String,
    val file: MultipartFile
)
package com.ninjabyte.puzzle.services

import org.springframework.web.multipart.MultipartFile

interface CloudinaryService {
    fun uploadFile(file: MultipartFile, folderName: String): String?
}
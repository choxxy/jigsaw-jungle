package com.ninjabyte.puzzle.services.impl

import com.cloudinary.Cloudinary
import com.ninjabyte.puzzle.services.CloudinaryService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Service
class CloudinaryServiceImpl(private val cloudinary: Cloudinary) : CloudinaryService {

    override fun uploadFile(file: MultipartFile, folderName: String): String? {
        return try {
            val options = HashMap<String, String>()
            options.put("folder", folderName)
            val uploadedFile = cloudinary.uploader().upload(file.getBytes(), options)
            val publicId = uploadedFile.get("public_id") as String?
            return cloudinary.url().secure(true).generate(publicId)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}
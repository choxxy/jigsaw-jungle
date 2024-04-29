package com.ninjabyte.puzzle.integration.transformer

import com.ninjabyte.puzzle.entities.Photo
import com.ninjabyte.puzzle.entities.toPhoto
import org.springframework.integration.file.transformer.AbstractFilePayloadTransformer
import java.io.*

class FileToPhotoTransformer : AbstractFilePayloadTransformer<Photo>() {
    @Throws(IOException::class)
    override fun transformFile(file: File): Photo {
        return   file.toPhoto()
    }
}
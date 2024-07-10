package com.ninjabyte.puzzle.services

import com.ninjabyte.puzzle.entities.toPhoto
import com.ninjabyte.puzzle.exceptions.StorageException
import com.ninjabyte.puzzle.exceptions.StorageFileNotFoundException
import org.apache.commons.io.FilenameUtils
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.stream.Stream


@Service
class FileSystemStorageService(private val photoDbService: PhotoDbService) : StorageService {

    private val rootLocation: Path = Paths.get("photos")

    override fun store(file: MultipartFile) {
        try {
            if (file.isEmpty) {
                throw StorageException("Failed to store empty file.")
            }


            // The name of the file is the relative path of the file in the client folder
            // Convert the client path separator to the server file path separator.
            val fileName: String = FilenameUtils.separatorsToSystem(file.originalFilename)


            // Resolve the absolute path to the file based on the public folder
            val filee: Path = rootLocation.resolve(fileName)

            val destinationFile = rootLocation.resolve(
                Paths.get(fileName)
            ).normalize().toAbsolutePath()

            if (destinationFile.parent != rootLocation.toAbsolutePath()) {
                // This is a security check
                throw StorageException("Cannot store file outside current directory.")
            }

            file.inputStream.use { inputStream ->
                Files.copy(
                    inputStream,
                    destinationFile,
                    StandardCopyOption.REPLACE_EXISTING
                )
            }

            val baseUrl: String =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

            //create the public Image URl where we can find the image
            val imageStringBuilder = StringBuilder(baseUrl)
            imageStringBuilder.append("/photos/")
            imageStringBuilder.append(file.originalFilename)

            val imageUrl = imageStringBuilder.toString()

            photoDbService.store(photo = file.toPhoto().apply {
                photoUrl = imageUrl
            })
        } catch (e: IOException) {
            throw StorageException("Failed to store file.", e)
        }
    }

    override fun loadAll(): Stream<Path> {
        try {
            return Files.walk(this.rootLocation, 1)
                .filter { path: Path -> path != rootLocation }
                .map { other: Path -> rootLocation.relativize(other) }
        } catch (e: IOException) {
            throw StorageException("Failed to read stored files", e)
        }
    }

    override fun load(filename: String): Path {
        return rootLocation.resolve(filename)
    }

    override fun loadAsResource(filename: String): Resource {
        try {
            val file = load(filename)
            val resource: Resource = UrlResource(file.toUri())
            if (resource.exists() || resource.isReadable) {
                return resource
            } else {
                throw StorageFileNotFoundException(
                    "Could not read file: $filename"
                )
            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("Could not read file: $filename", e)
        }
    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }

    override fun init() {
        try {
            Files.createDirectories(rootLocation)
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }
    }

    override fun store(photoPath: String) {
    }
}
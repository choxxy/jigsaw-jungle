package com.ninjabyte.puzzle.controllers

import com.ninjabyte.puzzle.entities.Image
import com.ninjabyte.puzzle.services.ImageService
import com.ninjabyte.puzzle.services.dtos.ImageModel
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*


@Controller
class HtmlController(
    private val imageService: ImageService
) {

    @GetMapping("/")
    fun home(model: Model): String {
        model["title"] = "Jigsaw Jungle"
        model["cssFile"] = "css/sample.css"
        model["photos"] = imageService.loadAll().map { it.render() }
        return "home"
    }

    @GetMapping("/admin")
    fun admin(model: Model): String {
        return "admin"
    }

    @GetMapping("/studio")
    fun studio(model: Model): String {
        model["images"] = imageService.loadAll().map { it.render() }
        return "studio"
    }

    @GetMapping("/puzzle")
    fun puzzle(
        @RequestParam(name = "id", required = true) id: String,
        model: Model
    ): String {
        val image = imageService.load(UUID.fromString(id))
        model["title"] = "Puzzle"
        model["cssFile"] = "css/puzzle.css"
        model["photoUrl"] = image.url
        model["photoId"] = image.id.toString()
        model["photos"] = imageService.loadAll().shuffled().take(8).map { it.render() }
        return "puzzle"
    }

    @PostMapping("/upload")
    fun upload(@RequestParam("file") file: MultipartFile, model: Model): String {

        val imageModel = ImageModel(
            name = file.name,
            file = file)

        if (imageModel.file.isEmpty) {
            model.addAttribute("message", "Please select a file to upload")
            return "studio"
        }
        try {
            imageService.uploadImage(imageModel)
            model.addAttribute("message", "File uploaded successfully: " + imageModel.name)
        } catch (e: Exception) {
            e.printStackTrace()
            model.addAttribute("message", "Failed to upload file: " + e.message)
        }

        model["photos"] = imageService.loadAll().map { it.render() }

        return "studio"
    }

   /* @GetMapping("/photos/{fileName:.+}")
    fun downloadFile(@PathVariable fileName: String, request: HttpServletRequest): ResponseEntity<Resource> {
        // Load file as Resource
        val resource: Resource = storageService.loadAsResource(fileName)

        // Try to determine file's content type
        var contentType: String? = null
        try {
            contentType = request.servletContext.getMimeType(resource.file.absolutePath)
        } catch (ex: IOException) {
            print("Could not determine file type.")
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream"
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                ("attachment; filename=\"" + resource.filename).toString() + "\""
            )
            .body<Resource>(resource)
    }*/

    fun Image.render() = RenderedPhoto(
        id,
        name,
        url,
        likes,
        playCount
    )

    data class RenderedPhoto(
        val id: UUID?,
        val name: String,
        val url: String,
        val likes: Int,
        val playCount: Int
    )
}
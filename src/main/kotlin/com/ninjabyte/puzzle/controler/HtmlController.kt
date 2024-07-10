package com.ninjabyte.puzzle.controler

import com.ninjabyte.puzzle.entities.Photo
import com.ninjabyte.puzzle.extensions.format
import com.ninjabyte.puzzle.services.PhotoDbService
import com.ninjabyte.puzzle.services.StorageService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.IOException


@Controller
class HtmlController(
    private val photoDbService: PhotoDbService,
    private val storageService: StorageService
) {

    @GetMapping("/")
    fun home(model: Model): String {
        model["title"] = "Puzzle"
        model["cssFile"] = "css/style.css"
        model["photos"] = photoDbService.loadAll().map { it.render() }
        return "home"
    }

    @GetMapping("/admin")
    fun admin(model: Model): String {
        return "admin"
    }

    @GetMapping("/studio")
    fun studio(model: Model): String {
        model["photos"] = photoDbService.loadAll().map { it.render() }
        return "studio"
    }

    @GetMapping("/puzzle")
    fun puzzle(
        @RequestParam(name = "id", required = true) id: String,
        model: Model
    ): String {
        val photo = photoDbService.load(id.toLong())
        model["title"] = "Puzzle"
        model["cssFile"] = "css/puzzle.css"
        model["photoUrl"] = photo.photoUrl
        model["photoId"] = photo.id
        model["photos"] = photoDbService.loadAll().shuffled().take(8).map { it.render() }
        return "puzzle"
    }

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile, model: Model): String {
        if (file.isEmpty) {
            model.addAttribute("message", "Please select a file to upload")
            return "studio"
        }
        try {
            storageService.store(file)
            model.addAttribute("message", "File uploaded successfully: " + file.originalFilename)
        } catch (e: IOException) {
            e.printStackTrace()
            model.addAttribute("message", "Failed to upload file: " + e.message)
        }
        storageService.loadAll().map { p ->
            println(p.toString())
        }
        model["photos"] = photoDbService.loadAll().map { it.render() }

        return "redirect:/"
    }

    @GetMapping("/photos/{fileName:.+}")
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
    }

    fun Photo.render() = RenderedPhoto(
        photoUrl,
        description,
        originalFilename,
        likes,
        played,
        createdOn.format(),
        createdBy,
        id
    )

    data class RenderedPhoto(
        val photoUrl: String,
        val description: String,
        val originalFilename: String,
        val likes: Int,
        val played: Int,
        val createdOn: String,
        val createdBy: String,
        val id: Long
    )
}
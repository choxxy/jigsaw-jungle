package com.ninjabyte.puzzle.controler

import com.ninjabyte.puzzle.entities.Photo
import com.ninjabyte.puzzle.extensions.format
import com.ninjabyte.puzzle.services.PhotoDbService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


@Controller
class HtmlController(private val photoDbService: PhotoDbService) {

    @GetMapping("/")
    fun home(model: Model): String {
        model["title"] = "Puzzle"
        model["cssFile"] = "css/style.css"
        model["photos"] = photoDbService.loadAll ().map { it.render() }
        return "index"
    }

    @GetMapping("/admin")
    fun admin(model: Model): String {
        return "admin"
    }

    @GetMapping("/puzzle")
    fun puzzle(model: Model): String {
        model["title"] = "Puzzle"
        model["cssFile"] = "css/puzzle.css"
        return "puzzle"
    }

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile, model: Model): String {
        if (file.isEmpty) {
            model.addAttribute("message", "Please select a file to upload")
            return "admin"
        }

        try {
            val bytes = file.bytes
            val path: Path = Paths.get("photos" + file.originalFilename)
            Files.write(path, bytes)
            model.addAttribute("message", "File uploaded successfully: " + file.originalFilename)
        } catch (e: IOException) {
            e.printStackTrace()
            model.addAttribute("message", "Failed to upload file: " + e.message)
        }

        return "admin"
    }

    fun Photo.render() = RenderedPhoto(
        description,
        likes,
        played,
        createdOn.format(),
        id
    )

    data class RenderedPhoto(
        val photoUrl: String,
        val likes: Int,
        val played: Int,
        val createdOn: String,
        val id: Long
    )
}
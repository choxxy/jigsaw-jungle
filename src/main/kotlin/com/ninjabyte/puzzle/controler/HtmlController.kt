package com.ninjabyte.puzzle.controler

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.ui.set

@Controller
class HtmlController {

    @GetMapping("/")
    fun home(model: Model): String {
        model["title"] = "Puzzle"
        model["cssFile"]="css/style.css"
        return "index"
    }

    @GetMapping("/puzzle")
    fun puzzle(model: Model): String {
        model["title"] = "Puzzle"
        model["cssFile"]="css/puzzle.css"
        return "puzzle"
    }
}
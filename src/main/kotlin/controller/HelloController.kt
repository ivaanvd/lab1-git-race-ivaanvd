package es.unizar.webeng.hello.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Controller
class HelloController(
    @param:Value("\${app.message:Hello World}") 
    private val message: String
) {
    
    @GetMapping("/")
    fun welcome(
        model: Model,
        @RequestParam(defaultValue = "") name: String
    ): String {
        val timeNow = java.time.Instant.now().toString()
        val hourNow = timeNow.substring(11, 13).toInt()
        val greetingTime = when (hourNow) {
            in 6..11 -> "Good morning"
            in 12..19 -> "Good afternoon"
            else -> "Good night"
        }
        val greeting = if (name.isNotBlank()) "$greetingTime, $name!" else message
        model.addAttribute("message", greeting)
        model.addAttribute("name", name)
        return "welcome"
    }
}

@RestController
class HelloApiController {
    
    @GetMapping("/api/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun helloApi(@RequestParam(defaultValue = "World") name: String): Map<String, String> {
        val timeNow = java.time.Instant.now().toString()
        val hourNow = timeNow.substring(11, 13).toInt()
        val greetingTime = when (hourNow) {
            in 6..11 -> "Good morning"
            in 12..19 -> "Good afternoon"
            else -> "Good night"
        }
        return mapOf(
            "message" to "$greetingTime, $name!",
            "timestamp" to java.time.Instant.now().toString()
        )
    }
}

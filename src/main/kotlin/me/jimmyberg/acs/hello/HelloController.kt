package me.jimmyberg.acs.hello

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/api/hello", "/api/hello/{name}")
    fun hello(@PathVariable name: String?) = "Hello ${name ?: "ACS"}!"

}
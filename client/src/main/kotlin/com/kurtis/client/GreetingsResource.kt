package com.kurtis.client

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/greetings")
class GreetingsResource(val helloClient: HelloClient) {

    @GetMapping(produces = ["application/json"])
    fun hello(): ResponseEntity<GreetingsResponse> {
        val response = helloClient.hello()
        return response
                ?.let { it -> ResponseEntity.ok(GreetingsResponse(it)) }
                ?: ResponseEntity.noContent().build()
    }
}

data class GreetingsResponse(val message: String)
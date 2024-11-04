package com.example.studylangchain4j.controller

import com.example.studylangchain4j.service.StreamChatAssistant
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/chatAssistant")
class ChatAssistantController {
    @Resource
    private lateinit var chatAssistant: StreamChatAssistant

    @GetMapping("/fluxChat/{message}")
    fun fluxChat(@PathVariable message: String): Flux<String> {
        return chatAssistant.fluxChat(message)
    }

    // 这里不会乱码
    @GetMapping("/test")
    fun test(): String {
        return "还会乱码？"
    }
}
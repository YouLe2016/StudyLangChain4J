package com.example.studylangchain4j.service

import dev.langchain4j.service.TokenStream
import reactor.core.publisher.Flux

/**
 * 聊天助手
 */
interface StreamChatAssistant {
    fun chat(message: String): TokenStream

    fun fluxChat(message: String): Flux<String>
}

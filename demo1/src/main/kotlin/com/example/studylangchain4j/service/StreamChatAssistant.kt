package com.example.studylangchain4j.service

import dev.langchain4j.service.TokenStream

/**
 * 聊天助手
 */
interface StreamChatAssistant {
    fun chat(message: String): TokenStream
}

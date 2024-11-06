package com.example.studylangchain4j.service

import dev.langchain4j.service.MemoryId
import dev.langchain4j.service.UserMessage

/**
 * 聊天助手
 */
interface ChatAssistant {
    fun chat(message: String): String

    fun chat(@MemoryId userId: Long, @UserMessage message: String): String
}

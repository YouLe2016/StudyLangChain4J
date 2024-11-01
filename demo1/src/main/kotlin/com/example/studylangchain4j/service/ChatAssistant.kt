package com.example.studylangchain4j.service

/**
 * 聊天助手
 */
interface ChatAssistant {
    fun chat(message: String): String
}

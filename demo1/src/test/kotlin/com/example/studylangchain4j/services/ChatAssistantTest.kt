package com.example.studylangchain4j.services

import com.example.studylangchain4j.service.ChatAssistant
import jakarta.annotation.Resource
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test


@SpringBootTest
class ChatAssistantTest {
    @Resource
    private lateinit var chatAssistant: ChatAssistant

    @Test
    fun testChat() {
        val chat = chatAssistant.chat("你好啊")
        println(chat)
    }

}

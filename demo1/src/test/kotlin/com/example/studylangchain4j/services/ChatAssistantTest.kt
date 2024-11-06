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

    @Test
    fun testMemory() {
        var question = "你好啊, 我的名字叫乐乐"
        println("question: $question")
        var answer = chatAssistant.chat(question)
        println("answer: $answer")
        question = "我的名字是什么？"
        println("question: $question")
        answer = chatAssistant.chat(question)
        println("answer: $answer")
    }

}

package com.example.studylangchain4j.services

import com.example.studylangchain4j.bean.LegalPrompt
import com.example.studylangchain4j.service.LegalAssistant
import jakarta.annotation.Resource
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class LegalAssistantTest {
    @Resource
    private lateinit var assistant: LegalAssistant

    @Test
    fun test1() {
        var question = "什么是著作权？"
        println("问题: $question \n\n答案: ${assistant.answerLegalQuestion(question)}")
        question = "什么是编程语言？"
        println("问题: $question \n\n答案: ${assistant.answerLegalQuestion(question)}")
    }

    @Test
    fun test2() {
        var question = LegalPrompt("劳动法", "被开除该怎么维权？")
        println("问题: $question \n\n答案: ${assistant.answerLegalQuestion(question)}")
        question = LegalPrompt("编程", "Kotlin语言是什么？")
        println("问题: $question \n\n答案: ${assistant.answerLegalQuestion(question)}")
    }
}
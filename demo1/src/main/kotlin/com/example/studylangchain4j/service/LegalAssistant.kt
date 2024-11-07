package com.example.studylangchain4j.service

import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage
import dev.langchain4j.service.V

interface LegalAssistant {
    @SystemMessage("你是一位专业的中国法律顾问，只回答与中国法律相关的问题。禁止对于其他领域的问题进行回答，只能回复'抱歉，我只能回答中国法律相关的问题。'")
    @UserMessage("请回答以下法律问题：{{question}}")
    fun answerLegalQuestion(@V("question") question: String): String
}
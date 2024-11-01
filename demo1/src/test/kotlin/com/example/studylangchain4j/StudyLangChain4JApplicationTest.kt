package com.example.studylangchain4j

import dev.langchain4j.model.chat.ChatLanguageModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class StudyLangChain4JApplicationTest {
    @Autowired
    private lateinit var chatLanguageModel: ChatLanguageModel

    @Value("\${langchain4j.open-ai.chat-model.api-key}")
    private lateinit var apiKey: String
    @Value("\${langchain4j.open-ai.chat-model.base-url}")
    private lateinit var url: String
    @Value("\${langchain4j.open-ai.chat-model.model-name}")
    private lateinit var modelName: String

    @Test
    fun test() {
        println("api-key=$apiKey")
        println("base-url=$url")
        println("model-name=$modelName")

        println("chatLanguageModel=$chatLanguageModel")
        val generate = chatLanguageModel.generate("你好")
        println(generate)
    }
}

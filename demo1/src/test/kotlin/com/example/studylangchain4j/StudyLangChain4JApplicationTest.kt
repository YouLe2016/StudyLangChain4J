package com.example.studylangchain4j

import dev.langchain4j.data.message.ImageContent
import dev.langchain4j.data.message.TextContent
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.model.chat.ChatLanguageModel
import jakarta.annotation.Resource
import org.springframework.core.io.Resource as Resource1
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.Test

@SpringBootTest
class StudyLangChain4JApplicationTest {
    @Resource(name="chatModel")
    private lateinit var chatLanguageModel: ChatLanguageModel

    @Value("\${langchain4j.open-ai.chat-model.api-key}")
    private lateinit var apiKey: String
    @Value("\${langchain4j.open-ai.chat-model.base-url}")
    private lateinit var url: String
    @Value("\${langchain4j.open-ai.chat-model.model-name}")
    private lateinit var modelName: String

    @Value("01.png")
    private lateinit var resource: Resource1

    @Test
    fun test() {
        println("api-key=$apiKey")
        println("base-url=$url")
        println("model-name=$modelName")

        println("chatLanguageModel=$chatLanguageModel")
        val generate = chatLanguageModel.generate("你好")
        println(generate)
    }

    @Test
    fun testChatMessage() {
        val generate = chatLanguageModel.generate(
            listOf(
                UserMessage.from("你好啊，这是谁"),
                UserMessage.from("进军", "这是我的AI朋友"),
                UserMessage.from("李白", "好的，问你个问题，咱们现在是几个人在聊天。都说了什么？"),
                UserMessage.from("进军", "你知道我们的名字吗？"),
            )
        )
        println(generate.content().text())
    }

    @Test
    fun testVLModel() {
        val byteArray = resource.contentAsByteArray
        val content = Base64.getEncoder().encodeToString(byteArray)
        val message = UserMessage.from(
            TextContent.from("请提取一下图片中的文字"),
            ImageContent.from(content, "image/png")
        )
        val generate = chatLanguageModel.generate(message)
        println(generate.content().text())
    }
}

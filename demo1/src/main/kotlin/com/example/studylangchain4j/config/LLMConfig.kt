package com.example.studylangchain4j.config

import com.example.studylangchain4j.service.ChatAssistant
import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.service.AiServices
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration(proxyBeanMethods = false)
class LLMConfig {
    @Value("\${langchain4j.open-ai.chat-model.api-key}")
    private lateinit var apiKey: String
    @Value("\${langchain4j.open-ai.chat-model.base-url}")
    private lateinit var url: String
    @Value("\${langchain4j.open-ai.chat-model.model-name}")
    private lateinit var modelName: String

    @Bean(name = ["chatModel"])
    fun createChatLanguageModel(): ChatLanguageModel {
        return OpenAiChatModel.builder()
            .apiKey(apiKey)
            .modelName(modelName)
            .baseUrl(url)
            .logRequests(true)
            .logResponses(true)
            .build()
    }

    @Bean
    fun createChatAssistant(@Qualifier("chatModel") model: ChatLanguageModel): ChatAssistant {
        return AiServices.create(ChatAssistant::class.java, model)
    }
}

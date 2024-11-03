package com.example.studylangchain4j.config

import com.example.studylangchain4j.listener.MyChatModelListener
import com.example.studylangchain4j.service.ChatAssistant
import com.example.studylangchain4j.service.StreamChatAssistant
import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.chat.StreamingChatLanguageModel
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiStreamingChatModel
import dev.langchain4j.service.AiServices
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration


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
            // 开启日志
//            .logRequests(true)
//            .logResponses(true)
            // 添加监听器
            .listeners(
                listOf(MyChatModelListener())
            )
            // 设置重试次数，默认3次
            .maxRetries(3)
            // 设置超时时间，默认60秒
            .timeout(Duration.ofSeconds(60))
            .build()
    }

    @Bean(name=["streamingChatModel"])
    fun createStreamingChatLanguageModel(): StreamingChatLanguageModel {
        return OpenAiStreamingChatModel.builder()
            .apiKey(apiKey)
            .modelName(modelName)
            .baseUrl(url)
            .build()
    }

    @Bean
    fun createChatAssistant(@Qualifier("chatModel") model: ChatLanguageModel): ChatAssistant {
        return AiServices.create(ChatAssistant::class.java, model)
    }
    @Bean
    fun createStreamChatAssistant(@Qualifier("streamingChatModel") model: StreamingChatLanguageModel): StreamChatAssistant {
        return AiServices.create(StreamChatAssistant::class.java, model)
    }
}

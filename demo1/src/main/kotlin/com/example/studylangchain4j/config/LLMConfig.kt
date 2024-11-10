package com.example.studylangchain4j.config

import com.example.studylangchain4j.invokehandler.InvoiceHandler
import com.example.studylangchain4j.service.*
import com.example.studylangchain4j.tools.ToolsFactory
import dev.langchain4j.memory.chat.MessageWindowChatMemory
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
            .logRequests(true)
            .logResponses(true)
            // 添加监听器
//            .listeners(
//                listOf(MyChatModelListener())
//            )
            // 设置重试次数，默认3次
            .maxRetries(3)
            // 设置超时时间，默认60秒
            .timeout(Duration.ofSeconds(30))
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

//    @Bean
//    fun createChatAssistant(@Qualifier("chatModel") model: ChatLanguageModel): ChatAssistant {
//        return AiServices.create(ChatAssistant::class.java, model)
//    }

//    @Bean
//    fun createChatAssistant(@Qualifier("chatModel") model: ChatLanguageModel): ChatAssistant {
//        val chatMemory = MessageWindowChatMemory.withMaxMessages(10)
//        return AiServices.builder(ChatAssistant::class.java)
//            .chatLanguageModel(model)
//            .chatMemory(chatMemory)
//            .build()
//    }

    @Bean
    fun createChatAssistant(@Qualifier("chatModel") model: ChatLanguageModel): ChatAssistant {
        return AiServices.builder(ChatAssistant::class.java)
            .chatLanguageModel(model)
            .chatMemoryProvider { memoryId ->
                println("chatMemoryProvider: memoryId=$memoryId")
                MessageWindowChatMemory.withMaxMessages(10)
            }
            .build()
    }

    @Bean
    fun createStreamChatAssistant(@Qualifier("streamingChatModel") model: StreamingChatLanguageModel): StreamChatAssistant {
        return AiServices.create(StreamChatAssistant::class.java, model)
    }

    @Bean
    fun createLegalAssistant(@Qualifier("chatModel") model: ChatLanguageModel): LegalAssistant {
        return AiServices.create(LegalAssistant::class.java, model)
    }

    @Bean
    fun createSentimentAnalyzer(@Qualifier("chatModel") model: ChatLanguageModel): SentimentAnalyzer {
        return AiServices.create(SentimentAnalyzer::class.java, model)
    }

    @Bean
    fun createNumberExtractor(@Qualifier("chatModel") model: ChatLanguageModel): NumberExtractor {
        return AiServices.create(NumberExtractor::class.java, model)
    }

    @Bean
    fun createPersonAnalyzer(@Qualifier("chatModel") model: ChatLanguageModel): PersonAnalyzer {
        return AiServices.create(PersonAnalyzer::class.java, model)
    }

    @Bean
    fun createFunctionAssistant(@Qualifier("chatModel") model: ChatLanguageModel): FunctionAssistant {
        return AiServices.builder(FunctionAssistant::class.java)
            .chatLanguageModel(model)
            .chatMemoryProvider { memoryId ->
                println("chatMemoryProvider: memoryId=$memoryId")
                MessageWindowChatMemory.withMaxMessages(10)
            }
//            .tools(ToolsFactory.createInvoiceAssistantTools())
            .tools(InvoiceHandler())
            .build()
    }
}

package com.example.studylangchain4j.config

import com.example.studylangchain4j.bean.PersonalityTrait
import com.example.studylangchain4j.service.*
import com.example.studylangchain4j.tools.PersonalityTraitFactory
import com.example.studylangchain4j.tools.ToolsFactory
import dev.langchain4j.agent.tool.graalvm.GraalVmPythonExecutionTool
import dev.langchain4j.classification.EmbeddingModelTextClassifier
import dev.langchain4j.classification.TextClassifier
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.memory.chat.TokenWindowChatMemory
import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.chat.StreamingChatLanguageModel
import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.model.openai.*
import dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.tool.ToolProviderResult
import dev.langchain4j.store.embedding.EmbeddingStore
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore
import io.qdrant.client.QdrantClient
import io.qdrant.client.QdrantGrpcClient
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

    @Value("\${langchain4j.open-ai.embedding-model.api-key}")
    private lateinit var embeddingApiKey: String

    @Value("\${langchain4j.open-ai.embedding-model.model-name}")
    private lateinit var embeddingModelName: String

    @Value("\${langchain4j.open-ai.embedding-model.base-url}")
    private lateinit var embeddingModelUrl: String

    @Bean
    fun embeddingModel(): EmbeddingModel {
        return OpenAiEmbeddingModel.builder()
            .apiKey(embeddingApiKey)
            .modelName(embeddingModelName)
            .baseUrl(embeddingModelUrl)
            .logRequests(true)
            .logResponses(true)
            .timeout(Duration.ofSeconds(30))
            .build()
    }

    @Bean
    fun createQdrantClient(): QdrantClient {
        val qdrantGrpcClient = QdrantGrpcClient.newBuilder("127.0.0.1", 6334, false)
            .build()
        return QdrantClient(qdrantGrpcClient)
    }

    @Bean
    fun embeddingStore(name: String): EmbeddingStore<TextSegment> {
        return QdrantEmbeddingStore.builder()
            .host("127.0.0.1")
            .port(6334)
            .collectionName(name)
            .build()
    }

    @Bean
    fun createCollectionName(): String {
        return "Truth"
    }

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

    @Bean(name = ["streamingChatModel"])
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
            .tools(GraalVmPythonExecutionTool())
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
//             .tools(ToolsFactory.createInvoiceAssistantTools())
//             .tools(InvoiceHandler())
            .toolProvider { request ->
                // 这里好像每一次回复，都会调用一次
                val message = request.userMessage()
                println("toolProvider: name=" + message.name())
                println("toolProvider: singleText=" + message.singleText())
                // val map = ToolsFactory.createInvoiceAssistantTools().entries.first()
                ToolProviderResult.builder()
                    // .add(map.key, map.value)
                    .addAll(ToolsFactory.createInvoiceAssistantTools())
                    .build()
            }
            .build()
    }

    @Bean
    fun createTextClassifier(embeddingModel: EmbeddingModel): TextClassifier<PersonalityTrait> {
        return EmbeddingModelTextClassifier(embeddingModel, PersonalityTraitFactory.examples)
    }

    @Bean
    fun createTruthAssistant(
        @Qualifier("chatModel") model: ChatLanguageModel,
        embeddingStore: EmbeddingStore<TextSegment>,
        embeddingModel: EmbeddingModel
    ): TruthAssistant{
        val retriever = EmbeddingStoreContentRetriever.builder()
            .embeddingStore(embeddingStore)
            .embeddingModel(embeddingModel)
            .build()
        return AiServices.builder(TruthAssistant::class.java)
            .chatLanguageModel(model)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
            .contentRetriever(retriever)
            .build()
    }
}

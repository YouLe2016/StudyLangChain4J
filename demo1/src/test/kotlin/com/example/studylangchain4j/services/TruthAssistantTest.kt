package com.example.studylangchain4j.services

import com.example.studylangchain4j.service.TruthAssistant
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.store.embedding.EmbeddingStore
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor
import jakarta.annotation.Resource
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class TruthAssistantTest {
    @Resource
    private lateinit var assistant: TruthAssistant
    @Resource
    private lateinit var embeddingStore: EmbeddingStore<TextSegment>
    @Resource
    private lateinit var embeddingModel: EmbeddingModel

    @Test
    fun test() {
        val path = "E:\\AlphaLearn\\2024真理城邑\\录入\\真圣新\\content\\2024\\11\\撒神的种子和收割与12枝派再创造.md"
        val document = FileSystemDocumentLoader.loadDocument(path)
        EmbeddingStoreIngestor.builder()
            .embeddingStore(embeddingStore)
            .build()
            .ingest(document)
    }

    @Test
    fun testQuestion() {
        val question = "不能加删启示录的经文是什么？"
        println("问题: $question \n\n答案: ${assistant.chat(question)}")
    }
}
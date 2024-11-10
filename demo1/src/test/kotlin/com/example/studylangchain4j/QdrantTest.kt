package com.example.studylangchain4j

import dev.langchain4j.data.embedding.Embedding
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.model.output.Response
import dev.langchain4j.store.embedding.EmbeddingSearchRequest
import dev.langchain4j.store.embedding.EmbeddingStore
import dev.langchain4j.store.embedding.filter.MetadataFilterBuilder
import io.qdrant.client.QdrantClient
import io.qdrant.client.grpc.Collections
import jakarta.annotation.Resource
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class QdrantTest {
    @Resource
    private lateinit var embeddingModel: EmbeddingModel

    @Resource
    private lateinit var qdrantClient: QdrantClient
    @Resource
    private lateinit var embeddingStore: EmbeddingStore<TextSegment>

    @Test
    fun testEmbeddingModel() {
        val embed: Response<Embedding> = embeddingModel.embed("你好啊")
        embed.metadata()["name"] = "李白"
        println(embed.content())
    }

    @Test
    fun createCollection() {
        val vectorParams = Collections.VectorParams.newBuilder()
            .setDistance(Collections.Distance.Cosine)
            .setSize(1024)
            .build();
        qdrantClient.createCollectionAsync("test", vectorParams)
    }

    @Test
    fun testText() {
        val segment = TextSegment.from("你好，我的名字叫乐哥哥")
        segment.metadata().put("author", "乐乐")
        val embedding: Embedding = embeddingModel.embed(segment).content()
        embeddingStore.add(embedding, segment)
//        (embeddingStore as QdrantEmbeddingStore).clearStore()
    }

    @Test
    fun testQuery1() {
        val embedding = embeddingModel.embed("我的名字叫什么？").content()
        val request = EmbeddingSearchRequest.builder()
            .queryEmbedding(embedding)
            .filter(MetadataFilterBuilder.metadataKey("author").isEqualTo("乐乐"))
            .maxResults(3)
            .build()
        val search = embeddingStore.search(request)
        search.matches().forEach {
            println("id: " + it.embeddingId())
            println("text: " + it.embedded().text())
            println("metadata: " + it.embedded().metadata())
            println("----------------")
        }
    }
}

package com.example.studylangchain4j.services

import com.example.studylangchain4j.service.StreamChatAssistant
import jakarta.annotation.Resource
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import kotlin.test.Test

@SpringBootTest
class StreamChatAssistantTest {
    @Resource
    private lateinit var chatAssistant: StreamChatAssistant

    @Test
    fun testChat() {
        val countDownLatch = CountDownLatch(1)
        val tokenStream = chatAssistant.chat("讲个笑话")
        tokenStream.onNext(::println)
            .onComplete {
                println(it.content().text())
                countDownLatch.countDown()
            }
            .onError(Throwable::printStackTrace)
            .start()
        countDownLatch.await()
    }
}

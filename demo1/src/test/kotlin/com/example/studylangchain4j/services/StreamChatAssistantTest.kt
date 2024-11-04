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
                println("------------完成------------")
                println(it.content().text())
                countDownLatch.countDown()
            }
            .onError(Throwable::printStackTrace)
            .start()
        countDownLatch.await()
    }

    @Test
    fun testFluxChat() {
        val countDownLatch = CountDownLatch(1)
        val stringBuilder = StringBuilder()
        val tokenFlux = chatAssistant.fluxChat("说一段悲伤的情话")
        tokenFlux.subscribe(
            {
                stringBuilder.append(it)
                println(it)
            },  // onNext
            Throwable::printStackTrace// onError
        ) {
            println("------------完成------------")
            // 在这漫长的旅途中，我遇见了你，如同夜空中最亮的星，照亮了我前行的道路。然而，美好的时光总是短暂，就像流星划过天际，留下一道美丽的痕迹后，便悄然消失。每当夜深人静，我仰望星空，心中满是对你的思念，却再也无法触及。愿你在远方也能感受到这份温柔的牵挂，即使我们不能共度每一个明天，但请记得，在这浩瀚的宇宙中，曾有一个灵魂深深地爱过你。
            // 在这寂静的夜晚，我望着星空，每一颗星星都像是你的眼睛，温柔而遥远。我想你，就像这夜空中的每一颗星辰，虽然它们看似近在咫尺，却永远无法触及。我们的故事，像是一首未完成的歌，旋律优美却带着一丝忧伤。我多么希望时间可以倒流，让我们重新开始，但我知道，有些美好只能留在记忆里，成为我们心中永恒的遗憾。愿你在未来的日子里，能找到属于你的幸福，而我会在这里，默默地祝福你，直到世界的尽头。
            println(stringBuilder)
            countDownLatch.countDown()
        } // onComplete
        countDownLatch.await()
    }
}

package com.example.studylangchain4j.services

import com.example.studylangchain4j.service.SentimentAnalyzer
import jakarta.annotation.Resource
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SentimentAnalyzerTest {
    @Resource
    private lateinit var analyzer: SentimentAnalyzer

    @Test
    fun test1() {
        val question = "假期结束开始上班"
        println("$question 是否具有正面情感: ${analyzer.isPositive(question)}")
        println("$question 情感分析: ${analyzer.analyzeSentimentOf(question)}")
    }
}
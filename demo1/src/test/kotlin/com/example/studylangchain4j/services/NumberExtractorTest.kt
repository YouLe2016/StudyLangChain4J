package com.example.studylangchain4j.services

import com.example.studylangchain4j.service.NumberExtractor
import jakarta.annotation.Resource
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NumberExtractorTest {
    @Resource
    private lateinit var extractor: NumberExtractor

    @Test
    fun test1() {
        val question = "我要请五天假去度蜜月，再请三天假回家。请领导批准"
        println("$question 请假天数：${extractor.extractInt(question)}")
    }
}
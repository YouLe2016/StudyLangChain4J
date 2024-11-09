package com.example.studylangchain4j.services

import com.example.studylangchain4j.service.PersonAnalyzer
import jakarta.annotation.Resource
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PersonAnalyzerTest {
    @Resource
    private lateinit var analyzer: PersonAnalyzer

    @Test
    fun test1() {
        val question = "张三，1990-01-01生"
        println("$question 人物信息：${analyzer.extractPerson(question)}")
    }

    @Test
    fun test2() {
        val question = "张三，1990-01-01生，李四，1980-02-03生"
        println("$question 人物信息：${analyzer.extractPersons(question).person}")
    }
}
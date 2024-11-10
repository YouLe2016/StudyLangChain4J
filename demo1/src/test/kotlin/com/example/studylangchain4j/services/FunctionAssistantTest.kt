package com.example.studylangchain4j.services

import com.example.studylangchain4j.service.FunctionAssistant
import jakarta.annotation.Resource
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class FunctionAssistantTest {
    @Resource
    private lateinit var assistant: FunctionAssistant

    @Test
    fun test1() {
        var question = "你好啊，请介绍一下你自己"
        println("问题: $question \n\n答案: ${assistant.chat(question)}")
        question = "帮我开具一张发票，单价：59.00，数量：1，合计：59.00，单位：元，纳税人识别号：91110101MA01AQZQ01，纳税人名称：河南智联科技有限公司"
        println("问题: $question \n\n答案: ${assistant.chat(question)}")
        question = "我还可以继续开票吗？"
        println("问题: $question \n\n答案: ${assistant.chat(question)}")
        question = "帮我开具一张发票，公司：华为科技有限公司，金额：1000.89，税号：zcx1553"
        println("问题: $question \n\n答案: ${assistant.chat(question)}")
    }
}

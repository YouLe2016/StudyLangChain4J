package com.example.studylangchain4j.service

import com.example.studylangchain4j.bean.Sentiment
import dev.langchain4j.service.UserMessage

interface SentimentAnalyzer {
    // text=假期结束开始上班
    @UserMessage("`{{it}}` 是否具有正面情感？")
    fun isPositive(text: String): Boolean

    // text=假期结束开始上班
    @UserMessage("分析 `{{it}}` 的情感")
    fun analyzeSentimentOf(text: String): Sentiment
}

package com.example.studylangchain4j.service

import dev.langchain4j.service.UserMessage

interface SentimentAnalyzer {
    // text=假期结束开始上班
    @UserMessage("`{{it}}` 是否具有正面情感？")
    fun isPositive(text: String): Boolean

    // text=假期结束开始上班
    @UserMessage("分析 `{{it}}` 的情感")
    fun analyzeSentimentOf(text: String): Sentiment

    enum class Sentiment {
        POSITIVE,  // 正面情感
        NEGATIVE,  // 负面情感
        NEUTRAL // 中立情感
    }
}

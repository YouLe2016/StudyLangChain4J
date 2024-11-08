package com.example.studylangchain4j.bean

import dev.langchain4j.model.input.structured.StructuredPrompt

@StructuredPrompt("根据中国{{legal}}法律，解答以下问题：{{question}}")
data class LegalPrompt(
    private val legal: String = "",
    private val question: String = "",
)
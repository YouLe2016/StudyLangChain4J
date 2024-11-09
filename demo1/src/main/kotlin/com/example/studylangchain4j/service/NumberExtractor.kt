package com.example.studylangchain4j.service

import dev.langchain4j.service.UserMessage
import java.math.BigDecimal
import java.math.BigInteger

interface NumberExtractor {
    // text=我赚了100块
    @UserMessage("Extract a number from {{it}}, please do calculations first if necessary")
    fun extractInt(text: String): Int

    // text=我赚了100块
    @UserMessage("Extract a long number from {{it}}")
    fun extractLong(text: String): Long

    // text=我赚了100块
    @UserMessage("Extract a big integer from {{it}}")
    fun extractBigInteger(text: String): BigInteger

    // text=我赚了100块
    @UserMessage("Extract a float number from {{it}}")
    fun extractFloat(text: String): Float

    // text=我赚了100块
    @UserMessage("Extract a double number from {{it}}")
    fun extractDouble(text: String): Double

    // text=我赚了100块
    @UserMessage("Extract a big decimal from {{it}}")
    fun extractBigDecimal(text: String): BigDecimal
}


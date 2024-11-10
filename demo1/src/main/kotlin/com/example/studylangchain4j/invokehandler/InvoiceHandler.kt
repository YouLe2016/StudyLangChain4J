package com.example.studylangchain4j.invokehandler

import dev.langchain4j.agent.tool.P
import dev.langchain4j.agent.tool.Tool
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class InvoiceHandler {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Tool(name = "invoiceAssistant", value = ["根据用户提交的开票信息进行开票"])
    fun invoiceAssistant(
        @P("公司名称，纳税人名称")companyName: String,
        @P("税号，纳税人识别号")dutyNumber: String,
        @P("金额。保留两位有效数字") amount: Double
    ): String {
        logger.info("companyName =>>>> {} dutyNumber =>>>> {} amount =>>>> {}", companyName, dutyNumber, amount)
        return "开票成功"
    }
}

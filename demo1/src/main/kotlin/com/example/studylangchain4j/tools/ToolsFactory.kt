package com.example.studylangchain4j.tools

import dev.langchain4j.agent.tool.JsonSchemaProperty
import dev.langchain4j.agent.tool.JsonSchemaProperty.description
import dev.langchain4j.agent.tool.ToolSpecification
import dev.langchain4j.service.tool.ToolExecutor

object ToolsFactory {
    fun createInvoiceAssistantTools() : Map<ToolSpecification, ToolExecutor> {
        val toolSpecification = ToolSpecification.builder()
            .name("invoice_assistant")
            .description("根据用户提交的开票信息，开具发票")
            .addParameter("companyName", JsonSchemaProperty.STRING, description("公司名称，纳税人名称"))
            .addParameter("dutyNumber", JsonSchemaProperty.STRING, description("税号，纳税人识别号"))
            // 建议使用String类型，再转一下数字类型
            .addParameter("amount", JsonSchemaProperty.NUMBER, description("金额, 保留两位有效数字"))
            .build()
        val toolExecutor = ToolExecutor { toolExecutionRequest, memoryId ->
            val arguments = toolExecutionRequest.arguments()
            println("memoryId=$memoryId, arguments =>>>> $arguments")
            "开票成功"
        }
        return mapOf(toolSpecification to toolExecutor)
    }
}

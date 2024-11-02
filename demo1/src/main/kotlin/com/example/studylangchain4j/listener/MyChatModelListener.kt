package com.example.studylangchain4j.listener

import dev.langchain4j.model.chat.listener.ChatModelErrorContext
import dev.langchain4j.model.chat.listener.ChatModelListener
import dev.langchain4j.model.chat.listener.ChatModelRequestContext
import dev.langchain4j.model.chat.listener.ChatModelResponseContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MyChatModelListener : ChatModelListener {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    override fun onRequest(requestContext: ChatModelRequestContext) {
        val request = requestContext.request()
        // 用来传递参数
        val attributes = requestContext.attributes()
        attributes["name"] = "MyChatModelListener"
        logger.info("onRequest: request={}", request)
        logger.info("onRequest: attributes={}", attributes)
    }

    override fun onResponse(responseContext: ChatModelResponseContext) {
        val request = responseContext.request()
        val response = responseContext.response()
        val attributes = responseContext.attributes()
        logger.info("onResponse: request={}", request)
        logger.info("onResponse: response={}", response)
        logger.info("onResponse: attributes={}", attributes)
    }

    override fun onError(errorContext: ChatModelErrorContext) {
        val error = errorContext.error()
        val request = errorContext.request()
        val response = errorContext.partialResponse()
        val attributes = errorContext.attributes()
        logger.info("onError: error=", error)
        logger.info("onError: request={}", request)
        logger.info("onError: partialResponse={}", response)
        logger.info("onError: attributes={}", attributes)
    }
}
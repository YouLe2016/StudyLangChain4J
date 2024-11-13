package com.example.studylangchain4j.service

interface TruthAssistant {
    fun chat(message: String): String
}
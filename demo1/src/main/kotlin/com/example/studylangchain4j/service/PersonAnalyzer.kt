package com.example.studylangchain4j.service

import com.example.studylangchain4j.bean.Person
import com.example.studylangchain4j.bean.PersonList
import dev.langchain4j.service.UserMessage

interface PersonAnalyzer {
    @UserMessage("Extract information about a person from {{it}}")
    fun extractPerson(text: String): Person

    @UserMessage("Extract information about people from {{it}}")
    fun extractPersons(text: String): PersonList
}
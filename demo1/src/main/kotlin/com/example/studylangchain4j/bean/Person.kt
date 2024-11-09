package com.example.studylangchain4j.bean

import java.time.LocalDate

data class Person(
    val name: String,
    val age: LocalDate
)

data class PersonList(
    val person: List<Person>,
)
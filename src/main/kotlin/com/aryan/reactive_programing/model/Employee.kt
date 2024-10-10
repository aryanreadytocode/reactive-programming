package com.aryan.reactive_programing.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Employee(
    @Id
    val id: String?,
    val name: String,
    val dept: String

) {

}

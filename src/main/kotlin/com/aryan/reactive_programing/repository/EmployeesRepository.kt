package com.aryan.reactive_programing.repository

import com.aryan.reactive_programing.model.Employee
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeesRepository: ReactiveMongoRepository<Employee, String> {
}
package com.aryan.reactive_programing

import com.aryan.reactive_programing.model.Employee
import com.aryan.reactive_programing.repository.EmployeesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.collectionExists
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class DBSeeder(
    @Autowired
    val employeesRepository: EmployeesRepository,
    @Autowired
    val reactiveMongoOperations: ReactiveMongoOperations
): CommandLineRunner {

    val employeeList = Flux.just(
        Employee(null, "Raj", "IT"),
        Employee(null, "Aryan", "IT"),
        Employee(null, "Vijay", "Mechanical"),
        Employee(null, "Rahul", "Civil")
    )
    override fun run(vararg args: String?) {
        dbSetup()
    }

    private fun dbSetup() {
        val employees = employeeList.flatMap {
            employeesRepository.save(it)
        }

        reactiveMongoOperations.collectionExists(Employee::class.java)
            .flatMap {
                when(it) {
                    true -> reactiveMongoOperations.dropCollection(Employee::class.java)
                    false -> Mono.empty()
                }
            }
            .thenMany(reactiveMongoOperations.createCollection(Employee::class.java))
            .thenMany(employees)
            .thenMany(employeesRepository.findAll())
            .subscribe({println(it)}, {println(it)}, {println("-- Database has been initialized")})
    }
}
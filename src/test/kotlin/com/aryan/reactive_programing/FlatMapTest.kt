package com.aryan.reactive_programing

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers.parallel
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier

/*map can be used for synchronous operation
* flatMap can be used for asynchronous opn*/
class FlatMapTest {

    @Test
    fun flatMapTest() {
        val employeeIds = listOf("1", "2", "3", "4", "5", "6,", "7", "8")

        val employeeName = Flux.fromIterable(employeeIds)
            .flatMap { getEmployeeDetails(it) }
            .log()

        StepVerifier.create(employeeName)
            .expectNextCount(8)
            .verifyComplete()
    }

    @Test
    fun flatMapTestUsingParallelScheduler() {
        val employeeIds = listOf("1", "2", "3", "4", "5", "6,", "7", "8")

        val employeeName = Flux.fromIterable(employeeIds)
            .window(2)
            .flatMap { data -> data.flatMap { getEmployeeDetails(it) }.subscribeOn(parallel()) }

            .log()

        StepVerifier.create(employeeName)
            .expectNextCount(8)
            .verifyComplete()
    }

    private fun getEmployeeDetails(id: String?): Mono<String> {
        val employees =  mapOf(
            "1" to "Raj",
            "2" to "Aryan",
            "3" to "Gopu",
            "4" to "Micky",
            "5" to "Rohan",
            "6" to "Hritik",
            "7" to "Salman",
            "8" to "Amir"
        )
        Thread.sleep(1000)
        return employees.getOrDefault(id, "Not found").toMono()
    }
}
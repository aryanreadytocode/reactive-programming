package com.aryan.reactive_programing

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class FilterExp {

    @Test
    fun filterTest1() {
        val cities = listOf("Patna", "Delhi", "Mumbai", "Kolkata", "Delhi", "Pune")

        val fluxCities = Flux.fromIterable(cities)
        val filteredCities = fluxCities.filter {it.startsWith("P")}

        StepVerifier.create(filteredCities.log())
            .expectNext("Patna")
            .expectNext("Pune")
            .verifyComplete()
    }

}
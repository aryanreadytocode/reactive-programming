package com.aryan.reactive_programing

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux

class MapExp {

    @Test
    fun mapTest1() {
        Flux.range(1, 9)
            .map { it.toString() + "String form" }
            .subscribe {println(it)}
    }

    @Test
    fun mapTest2() {
        Flux.range(1, 10)
            .map { it * it}
            .filter{ it %2 == 0 }
            .subscribe {println(it)}
    }
}
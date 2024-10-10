package com.aryan.reactive_programing

import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class MonoTest {

    @Test
    fun monoTest1() {
        val mono = Mono.just("Raj Aryan").log()

        StepVerifier.create(mono)
            .expectNext("Raj Aryan")
            .verifyComplete()
    }

    @Test
    fun monoTest2() {
        val mono = Mono.error<Exception>(Exception("Some exception"))

        StepVerifier.create(mono)
            .expectError(Exception::class.java)
            .verify()
    }
}
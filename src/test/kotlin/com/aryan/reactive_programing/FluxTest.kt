package com.aryan.reactive_programing

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import kotlin.reflect.jvm.jvmName

class FluxTest {

    /*verifyComplete trigger the flux, without it data will not emit*/
    @Test
    fun flux1() {
        val flux = Flux.just("Pawan", "Khesari", "Nirhua").log()

        StepVerifier.create(flux)
            .expectNext("Pawan", "Khesari", "Nirhua")
            .verifyComplete()
    }

    @Test
    fun flux2() {
        val flux = Flux.just("A", "B", "C").concatWith(Flux.error(RuntimeException("Error occurred")))

        StepVerifier
            .create(flux)
            .expectNext("A", "B", "C")
            .expectErrorMessage("Error occurred")
//            .expectError(RuntimeException::class.java)
            .verify()
    }
}
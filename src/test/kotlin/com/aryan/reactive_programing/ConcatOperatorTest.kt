package com.aryan.reactive_programing

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

class ConcatOperatorTest {

    @Test
    fun combineUsingMerge() {
        val flux1 = Flux.just("A", "B", "C")
        val flux2 = Flux.just("X", "Y", "Z")

        val combinedFlux = Flux.merge(flux1, flux2)

        StepVerifier.create(combinedFlux.log())
            .expectNext("A", "B", "C", "X", "Y", "Z")
            .verifyComplete()
    }

    @Test
    fun combineUsingMergeWithDelay() {
        val flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1))
        val flux2 = Flux.just("X", "Y", "Z").delayElements(Duration.ofSeconds(1))

        val combinedFlux = Flux.merge(flux1, flux2)

        StepVerifier.create(combinedFlux.log())
            .expectNextCount(6)
//            .expectNext("A", "B", "C", "X", "Y", "Z")
            .verifyComplete()
    }

    @Test
    fun combineWithConcat() {
        val flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1))
        val flux2 = Flux.just("X", "Y", "Z").delayElements(Duration.ofSeconds(1))

        val combinedFlux = Flux.concat(flux1, flux2)

        StepVerifier.create(combinedFlux.log())
            .expectNext("A", "B", "C", "X", "Y", "Z")
             .verifyComplete()
    }

    @Test
    fun combineWithZip() {
        val flux1 = Flux.just("A", "B", "C", "D")
        val flux2 = Flux.just("X", "Y", "Z", "0")

        val combineFlux = Flux.zip(flux1, flux2) // [A, X] [B, Y] [C, Z]
        val finalFlux = combineFlux.map { it.t1+it.t2 } // "AX", "BY", "CZ"

        StepVerifier.create(finalFlux.log())
            .expectNext("AX", "BY", "CZ", "D0")
            .verifyComplete()
    }
}
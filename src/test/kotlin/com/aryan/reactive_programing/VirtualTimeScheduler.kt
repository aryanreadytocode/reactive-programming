package com.aryan.reactive_programing

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

class VirtualTimeScheduler {

    @Test
    fun `simulate time operator`() {
        val flux = Flux.just(1, 2,3,4,5)
            .delayElements(Duration.ofSeconds(1))
            .log()

        StepVerifier.create(flux)
            .expectNext(1,2,3,4,5)
            .verifyComplete()
    }

    @Test
    internal fun `manipulating time using virtual time scheduler`() {
        reactor.test.scheduler.VirtualTimeScheduler.getOrSet()
        val flux = Flux.just(1, 2,3,4,5)
            .delayElements(Duration.ofSeconds(1))
            .log();
        StepVerifier.withVirtualTime {
            flux
        }
            .thenAwait(Duration.ofSeconds(2))
            .expectNext(1,2)
            .thenAwait(Duration.ofSeconds(3))
            .expectNext(3,4,5)
            .verifyComplete()
    }
}
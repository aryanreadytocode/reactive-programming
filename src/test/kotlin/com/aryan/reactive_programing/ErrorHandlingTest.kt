package com.aryan.reactive_programing

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.SignalType
import reactor.test.StepVerifier
import java.time.Duration

class ErrorHandlingTest {

    @Test
    fun errorHandlingTest() {
        val flux = Flux.just("A", "B", "C")
//            .concatWith(Flux.error(RuntimeException("some error")))
            .concatWith(Flux.just("D"))

        StepVerifier.create(flux.log())
            .expectNext("A", "B", "C", "D")
//            .expectError()
            .verifyComplete()
    }

    @Test
    fun doOnErrorTest() {
        val flux = Flux.just("A", "B", "C")
            .concatWith(Flux.error(RuntimeException("Some error")))
            .doOnError {
                println("Some error occurred $it")
            }

        StepVerifier.create(flux.log())
            .expectNext("A", "B", "C")
            .expectError()
            .verify()
    }

    @Test
    fun onErrorReturn() {
        val flux = Flux.just("A", "B", "C")
            .concatWith(Flux.error(RuntimeException("Some error")))
            .onErrorReturn("default value")

        StepVerifier.create(flux.log())
            .expectNext("A", "B", "C")
            .expectNext("default value")
            .verifyComplete()
    }

    @Test
    fun onErrorResume() {
        val flux = Flux.just("A", "B", "C")
            .concatWith(Flux.error(RuntimeException("Some error")))
            .onErrorResume {
                println("Some error occurred ${it.message}")
                Flux.just("D")
            }

        StepVerifier.create(flux.log())
            .expectNext("A", "B", "C")
            .expectNext("D")
            .verifyComplete()
    }

    @Test
    fun onErrorMap() {
        val flux = Flux.just("A", "B", "C")
            .concatWith(Flux.error(RuntimeException("Business Exception")))
            .onErrorMap {
                when {
                    it.message == "Business Exception" -> CustomException("Translated Exception")
                    else -> it
                }
            }

        StepVerifier.create(flux.log())
            .expectNext("A", "B", "C")
            .expectErrorMessage("Translated Exception")
            .verify()
    }

    @Test
    fun doFinallyTest() {
        val flux = Flux.just("A", "B", "C")
            .delayElements(Duration.ofSeconds(1))
            .doFinally {
                when (it) {
                    SignalType.CANCEL -> println("Perform operation on cancel")
                    SignalType.ON_COMPLETE -> println("Perform operation on complete")
                    SignalType.ON_ERROR -> println("Perform operation on error")
                    else -> println("nothing")
                }
            }
            .log()
            .take(2)

        StepVerifier.create(flux)
            .expectNext("A", "B")
            .thenCancel()
            .verify()
    }
}

class CustomException(errorMsg: String) : Throwable(errorMsg) {

}

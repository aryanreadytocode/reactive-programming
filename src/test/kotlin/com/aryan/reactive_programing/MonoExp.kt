package com.aryan.reactive_programing

import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono

class MonoExp {

    @Test
    fun mono1() {
        Mono.just("A")
            .log()
            .subscribe {println("My data is $it")}
    }

    @Test
    fun mono2() {
        Mono.error<Exception>(Exception("Some exception"))
            .log()
            .doOnError { println("Error occurred ${it.message}") }
            .subscribe()
    }


}
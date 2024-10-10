package com.aryan.reactive_programing

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import java.time.Duration

class FluxExp {

    /*static strem of data
    * being printed*/
    @Test
    fun fluxTest() {
        val flux = Flux.just("A", "B", "C")
        flux
            .log()
            .subscribe {println(it)}
    }

    /*subscribe is overloaded function
    * 2 Consumer
    * */
    @Test
    fun fluxWithError() {
        Flux.just("A", "B", "C")
            .concatWith(Flux.error(RuntimeException("Some error occured")))
            .log()
            .subscribe( {println(it)},
                {println("Error is $it")},
                { println("completed") }
            )
    }

    /*static data created from iterable*/
    @Test
    fun fluxWithIterable() {
        Flux.fromIterable(listOf("A", "B", "C"))
            .log()
            .subscribe {println(it)}
    }

    /*flux created with range*/
    @Test
    fun fluxWithRange() {
        Flux.range(5,6)
            .subscribe {println(it)}
    }

    /*flux create with interval
    * emmit data on interval mention on diff thread*/
    @Test
    fun fluxFromInterval() {
        Flux.interval(Duration.ofSeconds(1))
            .log()
            .subscribe {println(it)}
        Thread.sleep(5000)
    }

    /*using take
    * you can limit data you want to take
    * it call cancel once reach the value mention inside take*/
    @Test
    fun fluxWithTakeOperator() {
        Flux.interval(Duration.ofSeconds(1))
            .log()
            .take(2)
            .subscribe {println(it)}
        Thread.sleep(5000)
    }

    /*will use overloaded function of subscribe
    * subscription consumer
    * which tell no of item to request
    * it won't call on complete*/
    @Test
    fun fluxWithRequest() {
        Flux.range(1,11)
            .log()
            .subscribe({println(it)}, {}, {}, {it.request(2)})
        Thread.sleep(5000)
    }

    @Test
    fun fluxWithErrorHandling() {
        Flux.just("Aryan", "Raj", "Golu", "Molu")
            .concatWith(Flux.error(RuntimeException("Some Exception")))
            .onErrorReturn("some error happened")
            .log()
            .subscribe { println(it) }
        Thread.sleep(5000)
    }
}
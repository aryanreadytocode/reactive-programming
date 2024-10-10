package com.aryan.reactive_programing

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SchedulersTest {

    @Test
    fun defaultThreadingModel() {
        Mono.just("1").log()
            .map {
                log("map", it)
                it.toInt() }
            .subscribe()
    }

    private fun <T>log(operatorName: String, data: T) {
        println("Inside operator $operatorName, data is $data, Thread: " +
                "${Thread.currentThread().name}")
    }

    @Test
    fun timeOperator() {
        Mono.just("1").log()
            .delayElement(Duration.ofSeconds(1))
            .map {
                log("map", it)
                it.toInt() }
            .subscribe(::println)

        Thread.sleep(2000)
    }

    @Test
    fun schedulerExample() {
        Mono.just("1").log()
//            .publishOn(Schedulers.immediate())
//            .publishOn(Schedulers.single())
            .publishOn(Schedulers.boundedElastic())
            .map {
                log("map", it)
                it.toInt() }
            .subscribe()
    }

    @Test
    fun executorServiceExample() {
        Mono.just("1").log()
            .publishOn(Schedulers.fromExecutorService(executorService()))
            .map {
                log("map", it)
                it.toInt() }
            .subscribe()
    }

    fun executorService() : ExecutorService = Executors.newFixedThreadPool(10)

    @Test
    fun subscribeOnExample() {
        Mono.just("1").log()
            .map {
                log("map1", it)
                it.toInt()
            }
//            .subscribeOn(Schedulers.single())
            .publishOn(Schedulers.boundedElastic())
            .map {
                log("map2", it)
                it * 2
            }
            .subscribe(::println)
        Thread.sleep(1000)
    }

    @Test
    fun runOnOperator() {
        println("Number of CPU cores ${Runtime.getRuntime().availableProcessors()}")
        Flux.range(1, 20).log()
            .parallel().runOn(Schedulers.parallel())
            .map {
                log("map2", it)
                it
            }
            .subscribe(::println)
        Thread.sleep(1000)
    }

}
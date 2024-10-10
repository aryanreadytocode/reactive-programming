package com.aryan.reactive_programing

import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.kotlin.extra.retry.retryExponentialBackoff
import reactor.util.retry.Retry
import java.time.Duration

class RetryDemoTest {
    @Test
    internal fun `fixed number of retry`() {
        Mono.error<Exception>(RuntimeException("Some exception")).log()
            .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)))
            .subscribe()

        Thread.sleep(10000)
    }

    @Test
    internal fun `retry with minimum backoff`() {
        Mono.error<Exception>(RuntimeException("Some exception")).log()
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
            .subscribe()

        Thread.sleep(20000)
    }

    @Test
    internal fun `retry with exponential backoff`() {
        Mono.error<Exception>(RuntimeException("Some exception")).log()
            .retryExponentialBackoff(
                3,
                Duration.ofSeconds(1),
                Duration.ofSeconds(4),
                true
            )
            .subscribe()

        Thread.sleep(20000)
    }
}
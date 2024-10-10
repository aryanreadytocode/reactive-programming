package com.aryan.reactive_programing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.StepVerifier

/*
* WebFluxTest scans all the controller except services and repository*/
@ExtendWith(SpringExtension::class)
@WebFluxTest
class DemoControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

//    @Test
//    fun `flux API test 1`() {
//        webTestClient.get()
//            .uri("/flux")
//            .accept(MediaType.APPLICATION_JSON)
//            .exchange()
//            .expectStatus().is2xxSuccessful
//            .expectBody().json("""[1,2,3,4,5]""")
//    }

    @Test
    fun `flux API test 2`() {
        val response = webTestClient
            .get()
            .uri("/flux")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult(Int::class.java)
            .responseBody

        StepVerifier
            .create(response)
            .expectNext(1,2,3,4,5)
            .verifyComplete()
    }

    @Test
    internal fun `flux API test 3`() {
        webTestClient.get()
            .uri("/flux")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
    }
}
package io.sos.alisos.client

import io.micronaut.http.HttpRequest.POST
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Mock implementation of clinic interface
 *
 * @property httpClient http client that injected in class with parameters from application.yaml
 */
class MockClinicClientImpl : ClinicClient {


    @field:Client("clinic")
    @Inject
    private lateinit var httpClient: RxHttpClient


    override fun patient(user: UserClinicRequest): Flowable<HttpResponse<UserClinicResponse>> {
        return httpClient.exchange(POST("/api/patient", user), UserClinicResponse::class.java)
    }
}


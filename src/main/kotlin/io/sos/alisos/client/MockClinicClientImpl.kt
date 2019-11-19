package io.sos.alisos.client

import io.micronaut.http.HttpRequest.POST
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.reactivex.Flowable
import javax.inject.Inject


class MockClinicClientImpl : ClinicClient {


    @field:Client("clinic")
    @Inject
    private lateinit var httpClient: RxHttpClient


    override fun patient(user: UserClinicRequest): Flowable<HttpResponse<UserClinicResponse>> {
        return httpClient.exchange(POST("/patient", user), UserClinicResponse::class.java)
    }
}


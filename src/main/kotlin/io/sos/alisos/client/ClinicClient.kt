package io.sos.alisos.client

import io.micronaut.http.HttpResponse
import io.reactivex.Flowable


interface ClinicClient {
    fun patient(user: UserClinicRequest): Flowable<HttpResponse<UserClinicResponse>>?;
}
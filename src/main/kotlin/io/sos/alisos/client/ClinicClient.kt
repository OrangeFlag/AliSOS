package io.sos.alisos.client

import io.micronaut.http.HttpResponse
import io.reactivex.Flowable

/**
 * Interface to transfer patient information to the emergency clinic
 */
interface ClinicClient {
    /**
     * function to transfer patient information
     *
     * @param user user information
     * @return user information with the time of patient registration
     */
    fun patient(user: UserClinicRequest): Flowable<HttpResponse<UserClinicResponse>>?;
}
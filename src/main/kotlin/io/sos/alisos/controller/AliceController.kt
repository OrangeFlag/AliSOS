package io.sos.alisos.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.sos.alisos.domain.RequestWrapper
import io.sos.alisos.domain.Response
import io.sos.alisos.domain.ResponseWrapper
import io.sos.alisos.domain.User
import io.sos.alisos.service.AliceService
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Controller
class AliceController() {

    @Inject
    lateinit var service: AliceService

    var logger = LoggerFactory.getLogger(AliceController::class.java)

    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Post("/webhook")
    fun webhook(@Body request: RequestWrapper): ResponseWrapper {
        val response = ResponseWrapper(
            request.session,
            "1.0",
            Response()
        )

        val userId = request.session.userId

        val user = User(
            request.request.originalUtterance,
            request.request.nlu.entities.singleOrNull { x -> x.type == "YANDEX.GEO" }?.yandexGeo?.let { "${it.street} ${it.houseNumber}" },
            Regex("""\+?[\d-()]{10,20}""").let {
                it.find(request.request.originalUtterance)?.value
            }
        )

        response.response = service.webhook(userId, user)

        return response
    }
}
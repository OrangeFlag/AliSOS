package io.sos.alisos.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.sos.alisos.domain.MessageInfo
import io.sos.alisos.domain.RequestWrapper
import io.sos.alisos.domain.ResponseWrapper
import io.sos.alisos.service.AliceService
import org.slf4j.LoggerFactory
import javax.inject.Inject

/**
 * Controller for http request of our service
 *
 * @property service service with the logic of processing a received request
 */
@Controller
class AliceController {
    @Inject
    lateinit var service: AliceService

    var logger = LoggerFactory.getLogger(AliceController::class.java)

    /**
     * request handler from Yandex Alice
     */
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Post("/webhook")
    fun webhook(@Body request: RequestWrapper): ResponseWrapper {
        val userId = request.session.userId

        logger.info("webhook requested: userId: {}", userId)

        val user = MessageInfo(
            anamnesis = request.request.originalUtterance,
            address = request.request.nlu?.entities?.singleOrNull { x -> x.type == "YANDEX.GEO" }?.yandexGeo?.let { "${it.street} ${it.houseNumber}" },
            phone = Regex("""\+?[\d-()]{10,20}""").let {
                it.find(request.request.originalUtterance)?.value
            },
            yes = request.request.nlu?.tokens?.any { it.equals("да", true) } ?: false,
            no = request.request.nlu?.tokens?.any { it.equals("нет", true) } ?: false
        )

        logger.debug("webhook requested: userId: {}, user information: {}", userId, user)

        val response = service.webhook(userId, user)

        logger.debug("webhook service return: userId: {}, response: {}", userId, response)

        return ResponseWrapper(
            session = request.session,
            version = "1.0",
            response = response
        )
    }
}
package io.sos.alisos

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Controller
object AlisaController {

    @Inject
    lateinit var repository: Repository

    var logger = LoggerFactory.getLogger(AlisaController.javaClass)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Post("/webhook")
    fun webhook(@Body request: WebhookRequest): WebhookResponse {
        val response = WebhookResponse(request.session, "1.0")

        val user_id = request.session?.user_id!!
        repository.memory[user_id] = repository.memory.getOrDefault(user_id, User())
        val user = repository.memory[user_id]!!

        if (user.anamnesis == null) {
            user.anamnesis = request.request?.original_utterance
        } else {
            user.anamnesis += request.request?.original_utterance
        }

        if (user.address == null) {
            val geo =
                request.request?.nlu?.entities?.filter { x -> x.type == "YANDEX.GEO" }?.singleOrNull()?.yandexGeo!!
            user.address = "${geo.street} ${geo.house_number}"
        }

        val regex = """\+?[\d-()]{10,20}""".toRegex()
        val regexResult = regex.find(request.request?.original_utterance!!)
        if (regexResult?.value != null) {
            user.phone = regexResult.value
        }

        if (user.address == null && user.phone == null) {
            response.response = Response(false, "Чтобы позвать врача, нужны адрес и телефон.")
        } else if (user.address != null && user.phone == null) {
            response.response = Response(false, "Какой номер телефона передать врачу?")
        } else if (user.address == null && user.phone != null) {
            response.response = Response(false, "Куда приехать?")
        } else if (user.address != null && user.phone != null) {
            response.response = Response(true, "Готово! Сейчас позвонит врач.")
            logger.info("$user_id called an ambulance: ${user.address}; ${user.phone}; ${user.anamnesis}")
            repository.memory[user_id] = User()
        }

        return response
    }
}
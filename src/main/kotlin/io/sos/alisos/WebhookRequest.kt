package io.sos.alisos

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
open class Session {
    @NotBlank
    var session_id: String? = null
    @NotBlank
    var message_id: String? = null
    @NotBlank
    var user_id: String? = null
}

@Introspected
data class YandexGeo(
    var house_number: String? = null,
    var street: String? = null
)

@Introspected
data class YandexFIO(
    var first_name: String? = null,
    var last_name: String? = null
)

@Introspected
data class YandexNumber(
    var value: Int? = null
)

@Introspected
data class YandexDatetime(
    var day: String? = null,
    var day_is_relative: Boolean? = null
)

@Introspected
data class Entities(
    var type: String? = null,
    var yandexNumber: YandexNumber? = null,
    var yandexGeo: YandexGeo? = null,
    var yandexFIO: YandexFIO? = null,
    var yandexDatetime: YandexDatetime? = null
)

@Introspected
open class NLU {
    @NotBlank
    var tokens: Array<String>? = null
    @NotBlank
    var entities: Array<Entities>? = null

}

@Introspected
open class Request {
    @NotBlank
    var original_utterance: String? = null
    @NotBlank
    var nlu: NLU? = null
}

@Introspected
open class WebhookRequest {
    @NotBlank
    var session: Session? = null

    @NotBlank
    var request: Request? = null

}
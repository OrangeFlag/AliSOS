package io.sos.alisos.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
open class Session {

    @NotBlank
    lateinit var session_id: String

    @NotBlank
    lateinit var message_id: String

    @NotBlank
    lateinit var user_id: String
}

@Introspected
data class YandexGeo(
    var house_number: String,
    var street: String
)

@Introspected
data class YandexFIO(
    var first_name: String,
    var last_name: String
)

@Introspected
data class YandexNumber(
    var value: Int
)

@Introspected
data class YandexDatetime(
    var day: String,
    var day_is_relative: Boolean
)

@JsonDeserialize(using = EntitiesDeserializer::class)
@Introspected
data class Entities(
    var type: String,
    var yandexNumber: YandexNumber? = null,
    var yandexGeo: YandexGeo? = null,
    var yandexFIO: YandexFIO? = null,
    var yandexDatetime: YandexDatetime? = null
)

@Introspected
open class NLU {
    lateinit var tokens: Array<String>
    lateinit var entities: Array<Entities>

}

@Introspected
open class Request {

    lateinit var original_utterance: String

    lateinit var nlu: NLU
}

@Introspected
open class RequestWrapper {

    lateinit var session: Session

    lateinit var request: Request

}
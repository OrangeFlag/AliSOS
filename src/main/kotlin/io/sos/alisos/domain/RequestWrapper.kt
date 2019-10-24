package io.sos.alisos.domain

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal
import javax.validation.constraints.NotBlank

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@Introspected
open class Session {

    @NotBlank
    lateinit var sessionId: String

    var messageId: Int = 0

    @NotBlank
    lateinit var userId: String
}

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@Introspected
data class YandexGeo(
    var country: String?,
    var city: String?,
    var houseNumber: String?,
    var street: String?,
    var airport: String?
)

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@Introspected
data class YandexFio(
    var firstName: String?,
    var lastName: String?,
    var patronymicName: String?
)

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@Introspected
data class YandexDatetime(
    var year: String?,
    var yearIsRelative: Boolean?,
    var month: String?,
    var monthIsRelative: Boolean?,
    var day: String?,
    var dayIsRelative: Boolean?,
    var hour: String?,
    var hourIsRelative: Boolean?,
    var minute: String?,
    var minuteIsRelative: Boolean?
)

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@JsonDeserialize(using = EntitiesDeserializer::class)
@Introspected
data class Entity(
    var type: String,
    var yandexNumber: BigDecimal? = null,
    var yandexGeo: YandexGeo? = null,
    var yandexFio: YandexFio? = null,
    var yandexDatetime: YandexDatetime? = null
)

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@Introspected
open class NLU {
    lateinit var tokens: Array<String>
    lateinit var entities: Array<Entity>

}

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@Introspected
open class Request {

    lateinit var originalUtterance: String

    lateinit var nlu: NLU
}

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@Introspected
open class RequestWrapper {

    lateinit var session: Session

    lateinit var request: Request

}
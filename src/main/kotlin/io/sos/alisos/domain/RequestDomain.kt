package io.sos.alisos.domain

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal
import javax.validation.constraints.NotBlank

/**
 * User session
 *
 * @property sessionId Unique session identifier, maximum 64 characters.
 * @property messageId Session message identifier, maximum 8 characters.
 * @property userId The identifier of the application instance in which the user communicates with Alice is a maximum of 64 characters.
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@Introspected
open class Session {

    @NotBlank
    lateinit var sessionId: String

    var messageId: Int = 0

    @NotBlank
    lateinit var userId: String
}

/**
 * Location recognized by Alice from the user's message
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@Introspected
data class YandexGeo(
    var country: String?,
    var city: String?,
    var houseNumber: String?,
    var street: String?,
    var airport: String?
)

/**
 * Fio recognized by Alice from the user's message
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@Introspected
data class YandexFio(
    var firstName: String?,
    var lastName: String?,
    var patronymicName: String?
)

/**
 * Date recognized by Alice from the user's message
 */
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

/**
 * Entity recognized by Alice from the user's message
 */
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

/**
 * Words and named entities that Alice have extracted from a user request.
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@Introspected
open class NLU {
    lateinit var tokens: Array<String>
    lateinit var entities: Array<Entity>
}

/**
 * Data received from the user.
 *
 * @property originalUtterance full text of user request, maximum 1024 characters.
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@Introspected
open class Request {

    lateinit var originalUtterance: String

    var nlu: NLU? = null
}

/**
 * Data received from Alice.
 *
 * [Alice protocol docs](https://yandex.ru/dev/dialogs/alice/doc/protocol-docpage/)
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@Introspected
open class RequestWrapper {

    lateinit var session: Session

    lateinit var request: Request

}
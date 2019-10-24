package io.sos.alisos.domain

import io.micronaut.core.annotation.Introspected

@Introspected
data class Response(
    var end_session: Boolean = false,
    var text: String = "Повторите запрос"
)

@Introspected
data class ResponseWrapper(
    var session: Session,
    var version: String,
    var response: Response
)
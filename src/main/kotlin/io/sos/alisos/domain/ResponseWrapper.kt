package io.sos.alisos.domain

import io.micronaut.core.annotation.Introspected

@Introspected
data class Response(
    val end_session: Boolean = false,
    val text: String = "Повторите запрос",
    val buttons: List<Button> = emptyList()
)

@Introspected
data class Button(
    val title: String,
    val hide: Boolean = true
)

@Introspected
data class ResponseWrapper(
    val session: Session,
    val version: String,
    val response: Response
)
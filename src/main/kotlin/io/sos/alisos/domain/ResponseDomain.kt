package io.sos.alisos.domain

import io.micronaut.core.annotation.Introspected

/**
 * Response information
 *
 * @param end_session indicates the end of the dialogue, if true Alice will not wait for input from the user
 * @param text response text
 * @param buttons list of Alice user buttons
 */
@Introspected
data class Response(
    val end_session: Boolean = false,
    val text: String = "Повторите запрос",
    val buttons: List<Button> = emptyList()
)

/**
 * Buttons shown to the user in Alice's interface
 *
 * @param title text of button
 * @param hide if true, the button must be hidden after the user click
 */
@Introspected
data class Button(
    val title: String,
    val hide: Boolean = true
)

/**
 * Response in Alice's readable format
 *
 * @param session session in which Alice should return the answer
 * @param version version of Alice protocol
 * @param response response basic information
 */
@Introspected
data class ResponseWrapper(
    val session: Session,
    val version: String,
    val response: Response
)
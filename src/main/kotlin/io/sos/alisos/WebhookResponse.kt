package io.sos.alisos

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class Response(
    var end_session: Boolean? = null,
    var text: String? = null
)

@Introspected
data class WebhookResponse(
    var session: Session? = null,
    var version: String? = null,
    var response: Response? = null
)
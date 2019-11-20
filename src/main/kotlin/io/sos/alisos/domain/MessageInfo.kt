package io.sos.alisos.domain

data class MessageInfo(
    val anamnesis: String? = null,
    val address: String? = null,
    val phone: String? = null,
    val yes: Boolean = false,
    val no: Boolean = false
)
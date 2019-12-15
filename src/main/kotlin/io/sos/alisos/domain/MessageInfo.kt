package io.sos.alisos.domain

/**
 * Data class for all required fields from Alice request
 *
 * @param anamnesis description of user complaints
 * @param address address where the user is located
 * @param phone user phone
 * @param yes user clicked yes
 * @param no user clicked no
 */
data class MessageInfo(
    val anamnesis: String? = null,
    val address: String? = null,
    val phone: String? = null,
    val yes: Boolean = false,
    val no: Boolean = false
)
package io.sos.alisos.service

import io.sos.alisos.domain.MessageInfo
import io.sos.alisos.domain.Response

/**
 * Service Interface with logic for Alice request handler
 */
interface AliceService {
    /**
     * Main function to handle incoming requests
     * @param userId user id in \[A-Z1-9\]{64} format
     * @param messageInfo incoming user message information
     * @return response of service work with text
     */
    fun webhook(userId: String, messageInfo: MessageInfo): Response
}
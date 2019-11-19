package io.sos.alisos.service

import io.sos.alisos.domain.MessageInfo
import io.sos.alisos.domain.Response

interface AliceService {
    fun webhook(userId: String, messageInfo: MessageInfo): Response
}
package io.sos.alisos.service

import io.sos.alisos.domain.Response
import io.sos.alisos.domain.User

interface AliceService {
    fun webhook(userId: String, user: User): Response
}
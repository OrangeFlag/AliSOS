package io.sos.alisos.repository

import io.sos.alisos.domain.User
import javax.inject.Singleton


@Singleton
open class Repository() {
    protected var memory = HashMap<String, User>()

    operator fun get(user_id: String): User? {
        return memory[user_id]
    }

    operator fun set(user_id: String, user: User) {
        memory[user_id] = user
    }
}
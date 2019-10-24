package io.sos.alisos

import javax.inject.Singleton


data class User(
    var anamnesis: String? = null,
    var address: String? = null,
    var phone: String? = null
)

@Singleton
open class Repository {
    public var memory = HashMap<String, User>()
}
package io.sos.alisos.domain

data class User(
    val anamnesis: String? = null,
    val address: String? = null,
    val phone: String? = null
) {
    fun fill(other: User): User {
        return User(
            anamnesis ?: other.anamnesis,
            address ?: other.address,
            phone ?: other.phone
        )
    }
}
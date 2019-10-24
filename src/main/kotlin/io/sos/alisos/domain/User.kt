package io.sos.alisos.domain

data class User(
    var anamnesis: String? = null,
    var address: String? = null,
    var phone: String? = null
) {
    fun fill(other: User) {
        if (anamnesis == null) {
            anamnesis = other.anamnesis
        } else if (other.anamnesis != null) {
            anamnesis += other.anamnesis
        }

        if (other.address != null) {
            address = other.address
        }

        if (other.phone != null) {
            phone = other.phone
        }
    }
}
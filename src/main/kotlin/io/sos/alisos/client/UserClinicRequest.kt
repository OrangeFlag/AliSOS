package io.sos.alisos.client

data class UserClinicRequest(
    var userId: String,
    var anamnesis: String,
    var address: String,
    var phone: String,
    var doctorType: String?
)

data class UserClinicResponse(
    var userId: String,
    var anamnesis: String,
    var address: String,
    var phone: String,
    var doctorType: String?,
    var timestamp: Int
);
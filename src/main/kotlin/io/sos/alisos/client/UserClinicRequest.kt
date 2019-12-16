package io.sos.alisos.client

/**
 * Data that transfer to clinic
 *
 * @param userId user identificator
 * @param anamnesis description of user complaints
 * @param address address where the user is located
 * @param phone user phone
 * @param doctorType an assumption about which doctor the patient will need
 */
data class UserClinicRequest(
    var userId: String,
    var anamnesis: String,
    var address: String,
    var phone: String,
    var doctorType: String?
)

/**
 * Data received from the clinic
 *
 * @param userId user identificator
 * @param anamnesis description of user complaints
 * @param address address where the user is located
 * @param phone user phone
 * @param doctorType an assumption about which doctor the patient will need
 * @param timestamp request registration time
 */
data class UserClinicResponse(
    var userId: String,
    var anamnesis: String,
    var address: String,
    var phone: String,
    var doctorType: String?,
    var timestamp: Int
);
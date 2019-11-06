package io.sos.alisos.service

import io.micronaut.http.HttpStatus
import io.sos.alisos.client.MockClinicClientImpl
import io.sos.alisos.client.UserClinicRequest
import io.sos.alisos.db.UserRepository
import io.sos.alisos.domain.Response
import io.sos.alisos.domain.User
import org.slf4j.LoggerFactory
import javax.inject.Inject

open class AliceServiceImpl : AliceService {

    @Inject
    lateinit var userRepository: UserRepository


    @Inject
    lateinit var clinic: MockClinicClientImpl

    var logger = LoggerFactory.getLogger(AliceServiceImpl::class.java)

    override fun webhook(userId: String, user: User): Response {
        val userFromDb = userRepository.getOrCreate(userId)
        val updatedUser = userRepository.update(userFromDb.fill(user))

        logger.info("$userId request an ambulance: ${user.address}; ${user.phone}; ${user.anamnesis}")

        return if (updatedUser.anamnesis.isNullOrEmpty()) {
            Response(false, "Что случилось?")
        } else if (updatedUser.address == null && updatedUser.phone == null) {
            Response(false, "Чтобы позвать врача, нужны адрес и телефон.")
        } else if (updatedUser.address != null && updatedUser.phone == null) {
            Response(false, "Какой номер телефона передать врачу?")
        } else if (updatedUser.address == null && updatedUser.phone != null) {
            Response(false, "Куда приехать?")
        } else if (updatedUser.address != null && updatedUser.phone != null) {
            logger.info("$userId called an ambulance: ${user.address}; ${user.phone}; ${user.anamnesis}")
            val doctorType: String? = null
            clinic.patient(
                UserClinicRequest(
                    userId,
                    updatedUser.anamnesis,
                    updatedUser.address,
                    updatedUser.phone,
                    doctorType
                )
            )?.let {
                if (it.blockingFirst().status == HttpStatus.OK) {
                    userRepository.updateAnamnesis(userId, null)
                    return@let Response(true, "Готово! Сейчас позвонит врач.")
                }
                return@let null
            } ?: Response(true, "Извините, сервис временно недоступен")
        } else {
            Response()
        }
    }
}

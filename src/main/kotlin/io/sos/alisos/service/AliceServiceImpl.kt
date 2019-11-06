package io.sos.alisos.service

import io.micronaut.http.HttpStatus
import io.sos.alisos.client.MockClinicClientImpl
import io.sos.alisos.client.UserClinicRequest
import io.sos.alisos.domain.Response
import io.sos.alisos.domain.User
import io.sos.alisos.repository.Repository
import org.slf4j.LoggerFactory
import javax.inject.Inject

open class AliceServiceImpl() : AliceService {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var clinic: MockClinicClientImpl

    var logger = LoggerFactory.getLogger(AliceServiceImpl::class.java)

    override fun webhook(userId: String, user: User): Response {
        val actualUser = (repository[userId] ?: User()).fill(user)
        repository[userId] = actualUser

        logger.info("$userId request an ambulance: ${user.address}; ${user.phone}; ${user.anamnesis}")

        return if (actualUser.anamnesis.isNullOrEmpty()) {
            Response(false, "Что случилось?")
        } else if (actualUser.address == null && actualUser.phone == null) {
            Response(false, "Чтобы позвать врача, нужны адрес и телефон.")
        } else if (actualUser.address != null && actualUser.phone == null) {
            Response(false, "Какой номер телефона передать врачу?")
        } else if (actualUser.address == null && actualUser.phone != null) {
            Response(false, "Куда приехать?")
        } else if (actualUser.address != null && actualUser.phone != null) {
            logger.info("$userId called an ambulance: ${user.address}; ${user.phone}; ${user.anamnesis}")
            val doctorType: String? = null
            clinic.patient(
                UserClinicRequest(
                    userId,
                    actualUser.anamnesis,
                    actualUser.address,
                    actualUser.phone,
                    doctorType
                )
            )?.let {
                if (it.blockingFirst().status == HttpStatus.OK) {
                    repository[userId] = User(null, actualUser.address, actualUser.phone)
                    return@let Response(true, "Готово! Сейчас позвонит врач.")
                }
                return@let null
            } ?: Response(true, "Извините, сервис временно недоступен")
        } else {
            Response()
        }


    }
}

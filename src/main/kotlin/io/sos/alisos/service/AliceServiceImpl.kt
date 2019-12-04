package io.sos.alisos.service

import io.micronaut.http.HttpStatus
import io.sos.alisos.client.MockClinicClientImpl
import io.sos.alisos.client.UserClinicRequest
import io.sos.alisos.db.UserRecord
import io.sos.alisos.db.UserRepository
import io.sos.alisos.domain.Button
import io.sos.alisos.domain.MessageInfo
import io.sos.alisos.domain.Response
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import javax.inject.Inject

open class AliceServiceImpl : AliceService {

    val YES_NO_BUTTONS = listOf(Button("Да"), Button("Нет"))

    @Inject
    lateinit var userRepository: UserRepository


    @Inject
    lateinit var clinic: MockClinicClientImpl

    var logger = LoggerFactory.getLogger(AliceServiceImpl::class.java)

    override fun webhook(userId: String, messageInfo: MessageInfo): Response {
        val userFromDb = userRepository.getOrCreate(userId)
        val processedUser = processUpdates(userFromDb, messageInfo)

        userRepository.update(processedUser)

        logger.info("$userId request an ambulance: ${messageInfo.address}; ${messageInfo.phone}; ${messageInfo.anamnesis}")

        return if (processedUser.anamnesis.isNullOrEmpty()) {
            Response(false, "Что случилось?")
        } else if (processedUser.address == null && processedUser.phone == null) {
            Response(false, "Чтобы позвать врача, нужны адрес и телефон.")
        } else if (processedUser.address != null && processedUser.phone == null) {
            Response(false, "Какой номер телефона передать врачу?")
        } else if (processedUser.address == null && processedUser.phone != null) {
            Response(false, "Куда приехать?")
        } else if (processedUser.waitingForAddressConfirmation && processedUser.waitingForPhoneConfirmation) {
            Response(
                end_session = false,
                text = "Всё верно? Адрес: ${processedUser.address}, телефон: ${processedUser.phone}",
                buttons = YES_NO_BUTTONS
            )
        } else if (processedUser.waitingForAddressConfirmation) {
            Response(
                end_session = false,
                text = "Вы сейчас здесь? ${processedUser.address}",
                buttons = YES_NO_BUTTONS
            )
        } else if (processedUser.waitingForPhoneConfirmation) {
            Response(
                end_session = false,
                text = "Сюда можно будет позвонить? ${processedUser.phone}",
                buttons = YES_NO_BUTTONS
            )
        } else if (processedUser.address != null && processedUser.phone != null) {
            logger.info("$userId called an ambulance: ${messageInfo.address}; ${messageInfo.phone}; ${messageInfo.anamnesis}")
            val doctorType: String? = null
            clinic.patient(
                UserClinicRequest(
                    userId,
                    processedUser.anamnesis,
                    processedUser.address,
                    processedUser.phone,
                    doctorType
                )
            ).let {
                try {
                    return@let it.blockingFirst()
                } catch (e: Exception) {
                    return@let null
                }
            }?.let {
                if (it.status == HttpStatus.OK) {
                    userRepository.updateAnamnesis(userId, null)
                    return@let Response(true, "Готово! Сейчас позвонит врач.")
                }
                return@let null
            } ?: Response(true, "Извините, сервис временно недоступен")
        } else {
            Response()
        }
    }

    private fun processUpdates(user: UserRecord, updates: MessageInfo): UserRecord {
        return user
            .let {
                if (updates.no) {
                    processNo(it)
                } else if (updates.yes) {
                    processYes(it)
                } else it
            }
            .fill(updates)
            .let {
                setWaitingForConfirmationFlags(it)
            }
    }

    private fun setWaitingForConfirmationFlags(it: UserRecord): UserRecord {
        return it.copy(
            waitingForAddressConfirmation = it.addressDateModified?.isBefore(DateTime.now().minusHours(1)) ?: false,
            waitingForPhoneConfirmation = it.phoneDateModified?.isBefore(DateTime.now().minusHours(1)) ?: false
        )
    }

    private fun processYes(it: UserRecord): UserRecord {
        return it
            .let { if (it.waitingForAddressConfirmation) it.touchAddress() else it }
            .let { if (it.waitingForPhoneConfirmation) it.touchPhone() else it }
    }

    private fun processNo(it: UserRecord): UserRecord {
        return it
            .let { if (it.waitingForAddressConfirmation) it.clearAddress() else it }
            .let { if (it.waitingForPhoneConfirmation) it.clearPhone() else it }
    }
}

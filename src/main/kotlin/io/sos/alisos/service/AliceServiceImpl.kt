package io.sos.alisos.service

import io.sos.alisos.db.UserRepository
import io.sos.alisos.domain.Response
import io.sos.alisos.domain.User
import org.slf4j.LoggerFactory
import javax.inject.Inject

open class AliceServiceImpl : AliceService {

    @Inject
    lateinit var userRepository: UserRepository
    var logger = LoggerFactory.getLogger(AliceServiceImpl::class.java)

    override fun webhook(user_id: String, user: User): Response {
        val userFromDb = userRepository.getOrCreate(user_id)
        val updatedUser = userRepository.update(userFromDb.fill(user))

        logger.info("$user_id request an ambulance: ${user.address}; ${user.phone}; ${user.anamnesis}")

        return if (updatedUser.anamnesis.isNullOrEmpty()) {
            Response(false, "Что случилось?")
        } else if (updatedUser.address == null && updatedUser.phone == null) {
            Response(false, "Чтобы позвать врача, нужны адрес и телефон.")
        } else if (updatedUser.address != null && updatedUser.phone == null) {
            Response(false, "Какой номер телефона передать врачу?")
        } else if (updatedUser.address == null && updatedUser.phone != null) {
            Response(false, "Куда приехать?")
        } else if (updatedUser.address != null && updatedUser.phone != null) {
            logger.info("$user_id called an ambulance: ${user.address}; ${user.phone}; ${user.anamnesis}")
            userRepository.updateAnamnesis(user_id, "")
            Response(true, "Готово! Сейчас позвонит врач.")
        } else {
            Response()
        }
    }
}

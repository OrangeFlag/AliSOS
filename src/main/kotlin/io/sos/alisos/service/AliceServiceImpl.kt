package io.sos.alisos.service

import io.sos.alisos.domain.Response
import io.sos.alisos.domain.User
import io.sos.alisos.repository.Repository
import org.slf4j.LoggerFactory
import javax.inject.Inject

open class AliceServiceImpl() : AliceService {

    @Inject
    lateinit var repository: Repository

    var logger = LoggerFactory.getLogger(AliceServiceImpl::class.java)

    override fun webhook(user_id: String, user: User): Response {
        val actualUser = repository[user_id] ?: User()
        actualUser.fill(user)
        repository[user_id] = actualUser

        logger.info("$user_id request an ambulance: ${user.address}; ${user.phone}; ${user.anamnesis}")

        return if (actualUser.address == null && actualUser.phone == null) {
            Response(false, "Чтобы позвать врача, нужны адрес и телефон.")
        } else if (actualUser.address != null && actualUser.phone == null) {
            Response(false, "Какой номер телефона передать врачу?")
        } else if (actualUser.address == null && actualUser.phone != null) {
            Response(false, "Куда приехать?")
        } else if (actualUser.address != null && actualUser.phone != null) {
            logger.info("$user_id called an ambulance: ${user.address}; ${user.phone}; ${user.anamnesis}")
            repository[user_id] = User()
            Response(true, "Готово! Сейчас позвонит врач.")
        } else {
            Response()
        }


    }
}
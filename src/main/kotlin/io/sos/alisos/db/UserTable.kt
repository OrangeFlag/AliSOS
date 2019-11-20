package io.sos.alisos.db

import io.sos.alisos.domain.MessageInfo
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object UserTable : Table("users") {
    val id = varchar("id", 100).primaryKey()
    val anamnesis = text("anamnesis").nullable()
    val address = text("address").nullable()
    val phone = text("phone").nullable()
    val anamnesisDateModified = datetime("anamnesisDateModified").nullable()
    val addressDateModified = datetime("addressDateModified").nullable()
    val phoneDateModified = datetime("phoneDateModified").nullable()
    val waitingForAddressConfirmation = bool("waiting_for_address_confirmation").default(false)
    val waitingForPhoneConfirmation = bool("waiting_for_phone_confirmation").default(false)
}

data class UserRecord(
    val id: String,
    val anamnesis: String? = null,
    val address: String? = null,
    val phone: String? = null,
    val anamnesisDateModified: DateTime? = null,
    val addressDateModified: DateTime? = null,
    val phoneDateModified: DateTime? = null,
    val waitingForAddressConfirmation: Boolean = false,
    val waitingForPhoneConfirmation: Boolean = false
) {
    fun fill(messageInfo: MessageInfo): UserRecord =
        this
            .fillAnamnesis(messageInfo.anamnesis)
            .fillAddress(messageInfo.address)
            .fillPhone(messageInfo.phone)


    fun fillAnamnesis(anamnesis: String?): UserRecord =
        if (this.anamnesis != anamnesis) {
            val newAnamnesis = listOfNotNull(this.anamnesis, anamnesis).joinToString("\n")

            this.copy(anamnesis = newAnamnesis, anamnesisDateModified = DateTime.now())
        } else this


    fun fillAddress(address: String?): UserRecord =
        if (address != null && this.address != address) {
            this.copy(address = address, addressDateModified = DateTime.now())
        } else this

    fun fillPhone(phone: String?): UserRecord =
        if (phone != null && this.phone != phone) {
            this.copy(phone = phone, phoneDateModified = DateTime.now())
        } else this

    fun touchAddress(): UserRecord = this.copy(addressDateModified = DateTime.now())

    fun touchPhone(): UserRecord = this.copy(phoneDateModified = DateTime.now())

    fun clearAddress(): UserRecord = this.copy(address = null, addressDateModified = null)

    fun clearPhone(): UserRecord = this.copy(phone = null, phoneDateModified = null)
}

fun UserTable.rowToUserRecord(row: ResultRow): UserRecord =
    UserRecord(
        id = row[id],
        anamnesis = row[anamnesis],
        address = row[address],
        phone = row[phone],
        anamnesisDateModified = row[anamnesisDateModified],
        addressDateModified = row[addressDateModified],
        phoneDateModified = row[phoneDateModified],
        waitingForAddressConfirmation = row[waitingForAddressConfirmation],
        waitingForPhoneConfirmation = row[waitingForPhoneConfirmation]
    )


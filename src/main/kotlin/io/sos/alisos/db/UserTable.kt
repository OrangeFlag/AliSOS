package io.sos.alisos.db

import io.sos.alisos.domain.MessageInfo
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

/**
 * Repository table for user data
 */
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

/**
 *  Repository record for user data
 */
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
    /**
     * fill record with information from MessageInfo
     */
    fun fill(messageInfo: MessageInfo): UserRecord =
        this
            .fillAnamnesis(messageInfo.anamnesis)
            .fillAddress(messageInfo.address)
            .fillPhone(messageInfo.phone)


    /**
     * fill record with anamnesis from MessageInfo
     */
    fun fillAnamnesis(anamnesis: String?): UserRecord =
        if (this.anamnesis != anamnesis) {
            val newAnamnesis = listOfNotNull(this.anamnesis, anamnesis).joinToString("\n")

            this.copy(anamnesis = newAnamnesis, anamnesisDateModified = DateTime.now())
        } else this


    /**
     * fill record with address from MessageInfo
     */
    fun fillAddress(address: String?): UserRecord =
        if (address != null && this.address != address) {
            this.copy(address = address, addressDateModified = DateTime.now())
        } else this

    /**
     * fill record with phone from MessageInfo
     */
    fun fillPhone(phone: String?): UserRecord =
        if (phone != null && this.phone != phone) {
            this.copy(phone = phone, phoneDateModified = DateTime.now())
        } else this

    /**
     * fill modify date of address with the current date
     */
    fun touchAddress(): UserRecord = this.copy(addressDateModified = DateTime.now())

    /**
     * fill modify date of phone with the current date
     */
    fun touchPhone(): UserRecord = this.copy(phoneDateModified = DateTime.now())

    /**
     * clear address and modify date of address
     */
    fun clearAddress(): UserRecord = this.copy(address = null, addressDateModified = null)

    /**
     * clear phone and modify date of phone
     */
    fun clearPhone(): UserRecord = this.copy(phone = null, phoneDateModified = null)
}

/**
 * convert org.jetbrains.exposed.sql.ResultRow to [UserRecord]
 */
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


package io.sos.alisos.db

import io.sos.alisos.domain.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object UserTable : Table("users") {
    var id = varchar("id", 100).primaryKey()
    var anamnesis = text("anamnesis").nullable()
    var address = text("address").nullable()
    var phone = text("phone").nullable()
    var anamnesisDateModified = datetime("anamnesisDateModified").nullable()
    var addressDateModified = datetime("addressDateModified").nullable()
    var phoneDateModified = datetime("phoneDateModified").nullable()
}

data class UserRecord(
    val id: String,
    val anamnesis: String? = null,
    val address: String? = null,
    val phone: String? = null,
    val anamnesisDateModified: DateTime? = null,
    val addressDateModified: DateTime? = null,
    val phoneDateModified: DateTime? = null
) {
    fun fill(user: User): UserRecord =
        this
            .fillAnamnesis(user.anamnesis)
            .fillAddress(user.address)
            .fillPhone(user.phone)


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

}

fun UserTable.rowToUserRecord(row: ResultRow): UserRecord =
    UserRecord(
        id = row[id], anamnesis = row[anamnesis], address = row[address],
        phone = row[phone], anamnesisDateModified = row[anamnesisDateModified],
        addressDateModified = row[addressDateModified],
        phoneDateModified = row[phoneDateModified]
    )


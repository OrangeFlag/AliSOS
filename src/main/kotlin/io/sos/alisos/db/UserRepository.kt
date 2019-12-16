package io.sos.alisos.db

import io.micronaut.context.annotation.Property
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import javax.annotation.PostConstruct
import javax.inject.Singleton

/**
 * Repository for users data
 *
 * @property url repository url
 * @property driver repository driver
 * @property user repository user
 * @property password repository password
 */
@Singleton
class UserRepository {
    @field:Property(name = "database.url")
    lateinit var url: String

    @field:Property(name = "database.driver")
    lateinit var driver: String

    @field:Property(name = "database.user")
    lateinit var user: String

    @field:Property(name = "database.password")
    lateinit var password: String

    /**
     * @suppress
     */
    @PostConstruct
    fun initialize() {
        Database.connect(
            url = url,
            driver = driver,
            user = user,
            password = password
        )

        transaction {
            SchemaUtils.createMissingTablesAndColumns(UserTable)
        }
    }

    /**
     * insert record to repository
     */
    fun insert(user: UserRecord): UserRecord {
        UserTable.insert {
            it[id] = user.id
            it[anamnesis] = user.anamnesis
            it[phone] = user.phone
            it[address] = user.address
            it[anamnesisDateModified] = user.anamnesisDateModified
            it[addressDateModified] = user.addressDateModified
            it[phoneDateModified] = user.phoneDateModified
            it[waitingForAddressConfirmation] = user.waitingForAddressConfirmation
            it[waitingForPhoneConfirmation] = user.waitingForPhoneConfirmation
        }

        return this[user.id]
    }


    /**
     * update record in repository
     */
    fun update(user: UserRecord): UserRecord {
        transaction {
            execUpdate(user.id) {
                it[anamnesis] = user.anamnesis
                it[address] = user.address
                it[phone] = user.phone
                it[anamnesisDateModified] = user.anamnesisDateModified
                it[addressDateModified] = user.addressDateModified
                it[phoneDateModified] = user.phoneDateModified
                it[waitingForAddressConfirmation] = user.waitingForAddressConfirmation
                it[waitingForPhoneConfirmation] = user.waitingForPhoneConfirmation
            }
        }

        return this[user.id]
    }

    /**
     * update user anamnesis by record id
     */
    fun updateAnamnesis(id: String, anamnesis: String?): UserRecord {
        transaction {
            execUpdate(id) {
                it[this.anamnesis] = anamnesis
                it[this.anamnesisDateModified] = DateTime.now()
            }
        }

        return this[id]
    }

    /**
     * update user address by record id
     */
    fun updateAddress(id: String, address: String?): UserRecord {
        transaction {
            execUpdate(id) {
                it[this.address] = address
                it[this.addressDateModified] = DateTime.now()
            }
        }

        return this[id]
    }

    /**
     * update user phone by record id
     */
    fun updatePhone(id: String, phone: String?): UserRecord {
        transaction {
            execUpdate(id) {
                it[this.phone] = phone
                it[this.phoneDateModified] = DateTime.now()
            }
        }

        return this[id]
    }

    private fun execUpdate(id: String, updateStatement: UserTable.(UpdateStatement) -> Unit): Int {
        val rowsUpdated = UserTable.update({ UserTable.id eq id }, body = updateStatement)

        if (rowsUpdated != 1)
            throw RuntimeException("Couldn't update value")

        return rowsUpdated
    }

    /**
     * get user record by id
     */
    operator fun get(id: String): UserRecord = transaction { findOneById(id) }
        ?: throw Exception("UserRecord not found")

    /**
     * get user record by id or insert new record with that id
     */
    fun getOrCreate(id: String): UserRecord = transaction {
        findOneById(id) ?: insert(UserRecord(id))
    }

    private fun findOneById(id: String): UserRecord? =
        UserTable.select { UserTable.id eq id }
            .limit(1)
            .map { it.toUserRecord() }
            .firstOrNull()

    private fun findByIdList(ids: List<String>): List<UserRecord> =
        UserTable.select { UserTable.id inList ids.distinct() }
            .map { it.toUserRecord() }

    private fun findAll(): List<UserRecord> = UserTable.selectAll().map { it.toUserRecord() }
}

private fun ResultRow.toUserRecord() = UserTable.rowToUserRecord(this)
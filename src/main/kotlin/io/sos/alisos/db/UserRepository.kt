package io.sos.alisos.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import javax.inject.Singleton

@Singleton
class UserRepository {
    fun insert(user: UserRecord): UserRecord {
        UserTable.insert {
            it[id] = user.id
            it[anamnesis] = user.anamnesis
            it[phone] = user.phone
            it[address] = user.address
            it[anamnesisDateModified] = user.anamnesisDateModified
            it[addressDateModified] = user.addressDateModified
            it[phoneDateModified] = user.phoneDateModified
        }

        return this[user.id]
    }

    fun update(user: UserRecord): UserRecord {
        transaction {
            execUpdate(user.id) {
                it[anamnesis] = user.anamnesis
                it[address] = user.address
                it[phone] = user.phone
                it[anamnesisDateModified] = user.anamnesisDateModified
                it[addressDateModified] = user.addressDateModified
                it[phoneDateModified] = user.phoneDateModified
            }
        }

        return this[user.id]
    }

    fun updateAnamnesis(id: String, anamnesis: String): UserRecord {
        transaction {
            execUpdate(id) {
                it[this.anamnesis] = anamnesis
                it[this.anamnesisDateModified] = DateTime.now()
            }
        }

        return this[id]
    }

    fun updateAddress(id: String, address: String): UserRecord {
        transaction {
            execUpdate(id) {
                it[this.address] = address
                it[this.addressDateModified] = DateTime.now()
            }
        }

        return this[id]
    }

    fun updatePhone(id: String, phone: String): UserRecord {
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

    operator fun get(id: String): UserRecord = transaction { findOneById(id) }
        ?: throw Exception("UserRecord not found")

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
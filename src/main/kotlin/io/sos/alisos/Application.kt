package io.sos.alisos

import io.micronaut.runtime.Micronaut
import io.sos.alisos.db.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        Database.connect(
            url = System.getenv("DB_URL"), driver = "org.postgresql.Driver",
            user = System.getenv("DB_USER"), password = System.getenv("DB_PASSWORD")
        )
        transaction {
            SchemaUtils.createMissingTablesAndColumns(UserTable)
        }
        Micronaut.run(Application.javaClass)
    }
}
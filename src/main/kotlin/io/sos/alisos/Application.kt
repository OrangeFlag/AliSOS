package io.sos.alisos

import io.micronaut.runtime.Micronaut

/**
 * Main object for run Application
 */
object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.run(Application.javaClass)
    }
}
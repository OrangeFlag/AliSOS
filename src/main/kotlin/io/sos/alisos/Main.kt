package io.sos.alisos

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/hello")
class HelloController {
    @Get(produces = [MediaType.TEXT_JSON])
    fun index(): String {
        return "Hello World"
    }
}
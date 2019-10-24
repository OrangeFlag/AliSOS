package io.sos.alisos

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.core.JsonProcessingException
import java.io.IOException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer


class EntitiesDeserializer : JsonDeserializer<Entities>() {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): Entities {

        val oc = jp.codec
        val node = oc.readTree<JsonNode>(jp)

        val type = node.get("type").asText()

        val value = node.get("value")

        return when (type) {
            "YANDEX.GEO" -> {
                return Entities(
                    "YANDEX.GEO", yandexGeo = YandexGeo(
                        value.get("house_number").asText(),
                        value.get("street").asText()
                    )
                )
            }
            "YANDEX.FIO" -> {
                return Entities(
                    "YANDEX.FIO", yandexFIO = YandexFIO(
                        value.get("first_name").asText(),
                        value.get("last_name").asText()
                    )
                )
            }
            "YANDEX.NUMBER" -> {
                return Entities("YANDEX.NUMBER", yandexNumber = YandexNumber(value.asInt()))
            }
            "YANDEX.DATETIME" -> {
                return Entities(
                    "YANDEX.DATETIME", yandexDatetime = YandexDatetime(
                        value.get("day").asText(),
                        value.get("day_is_relative").asBoolean()
                    )
                )
            }
            else -> {
                Entities()
            }

        }

    }

}
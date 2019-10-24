package io.sos.alisos.domain

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.core.JsonProcessingException
import java.io.IOException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.lang.IllegalArgumentException
import java.math.BigDecimal


class EntitiesDeserializer : JsonDeserializer<Entity?>() {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): Entity? {

        val objectCodec = jp.codec
        val node = objectCodec.readTree<JsonNode>(jp)

        val type = node.get("type").asText()

        val value = node.get("value")

        return when (type) {
            "YANDEX.GEO" -> {
                val parsedObject = objectCodec.treeToValue(value, YandexGeo::class.java)
                Entity(type, yandexGeo = parsedObject)
            }
            "YANDEX.FIO" -> {
                val parsedObject = objectCodec.treeToValue(value, YandexFio::class.java)
                Entity(type, yandexFio = parsedObject)
            }
            "YANDEX.NUMBER" -> {
                val parsedObject = objectCodec.treeToValue(value, BigDecimal::class.java)
                Entity(type, yandexNumber = parsedObject)
            }
            "YANDEX.DATETIME" -> {
                val parsedObject = objectCodec.treeToValue(value, YandexDatetime::class.java)
                Entity(type, yandexDatetime = parsedObject)
            }
            else -> throw IllegalArgumentException("$type type is not supported")

        }

    }

}
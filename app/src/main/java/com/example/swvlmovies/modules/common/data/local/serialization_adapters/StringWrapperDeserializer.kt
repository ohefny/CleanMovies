package com.example.swvlmovies.modules.common.data.local.serialization_adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class StringWrapperDeserializer<T>(private val serializer: (String) -> T) : JsonDeserializer<T> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): T {
        return if (json!!.asJsonPrimitive.isString)
            serializer.invoke(json.asString)
        else
            throw IllegalArgumentException("can't convert non string to $typeOfT")
    }
}
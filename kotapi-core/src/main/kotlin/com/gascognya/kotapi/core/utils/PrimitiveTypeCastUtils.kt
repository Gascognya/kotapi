@file:Suppress("UNCHECKED_CAST")

package com.gascognya.kotapi.core.utils

import java.util.*
import kotlin.reflect.KClass

object PrimitiveTypeCastUtils {
    fun <T : Any> convert(raw: String, cls: KClass<T>): T? {
        try {
            return when (cls) {
                String::class -> raw
                Boolean::class -> raw.lowercase(Locale.getDefault()).toBooleanStrict()
                Int::class -> raw.toInt()
                Byte::class -> raw.toByte()
                Short::class -> raw.toShort()
                Long::class -> raw.toLong()
                Float::class -> raw.toFloat()
                Double::class -> raw.toDouble()
                else -> null
            } as? T
        } catch (t: Throwable){
            throw TypeCastException("string: `$raw` can't convert to cls: `$cls`")
        }
    }

    fun <T : Any> strictConvert(raw: String, cls: KClass<T>): T {
        try {
            return when (cls) {
                String::class -> raw
                Boolean::class -> raw.lowercase(Locale.getDefault()).toBooleanStrict()
                Int::class -> raw.toInt()
                Byte::class -> raw.toByte()
                Short::class -> raw.toShort()
                Long::class -> raw.toLong()
                Float::class -> raw.toFloat()
                Double::class -> raw.toDouble()
                else -> throw TypeCastException("string: `$raw` can't convert to cls: `$cls`")
            } as T
        } catch (t: Throwable){
            throw TypeCastException("string: `$raw` can't convert to cls: `$cls`")
        }
    }
}
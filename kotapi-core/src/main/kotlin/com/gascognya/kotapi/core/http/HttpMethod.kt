@file:Suppress("MemberVisibilityCanBePrivate")

package com.gascognya.kotapi.core.http

import java.util.*

@JvmInline
value class HttpMethod(private val value: UByte) {
    operator fun contains(other: HttpMethod): Boolean = (value and other.value) != UByte.MIN_VALUE
    operator fun plus(other: HttpMethod): HttpMethod = HttpMethod(value or other.value)
    fun split(): List<HttpMethod>{
        var value = value
        val list = mutableListOf<HttpMethod>()

        while (value != UByte.MIN_VALUE){
            val uInt = value.inv() + 1u
            val m = value and (uInt.toUByte())
            list.add(HttpMethod(m))
            value = value xor m
        }
        return list
    }
    fun count(): Int {
        var num = value.toInt()
        var count = 0
        while (num > 0){
            num = num and (num - 1)
            count ++
        }
        return count
    }

    override fun toString(): String {
        return split().joinToString(" ") { castMap[it.value]!! }
    }

    companion object{
        val None = HttpMethod(0b00000000.toUByte())
        val Get = HttpMethod(0b00000001.toUByte())
        val Post = HttpMethod(0b00000010.toUByte())
        val Delete = HttpMethod(0b00000100.toUByte())
        val Put = HttpMethod(0b00001000.toUByte())
        val Patch = HttpMethod(0b00010000.toUByte())
        val Head = HttpMethod(0b00100000.toUByte())
        val Options = HttpMethod(0b01000000.toUByte())
        val Trace = HttpMethod(0b10000000.toUByte())
        val All = HttpMethod(0b11111111.toUByte())

        val castMap = mutableMapOf(
            0b00000000.toUByte() to "None",
            0b00000001.toUByte() to "Get",
            0b00000010.toUByte() to "Post",
            0b00000100.toUByte() to "Delete",
            0b00001000.toUByte() to "Put",
            0b00010000.toUByte() to "Patch",
            0b00100000.toUByte() to "Head",
            0b01000000.toUByte() to "Option",
            0b10000000.toUByte() to "Trace",
        )

        fun from(m: String): HttpMethod {
            return when(m.uppercase(Locale.getDefault())){
                "GET" -> Get
                "POST" -> Post
                "DELETE" -> Delete
                "PUT" -> Put
                "PATCH" -> Patch
                "HEAD" -> Head
                "OPTIONS" -> Options
                "TRACE" -> Trace
                else -> None
            }
        }
    }
}

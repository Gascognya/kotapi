package com.gascognya.kotapi.servlet

import com.gascognya.kotapi.core.http.HttpCookie
import jakarta.servlet.http.Cookie

class ServletCookie(val raw: Cookie) : HttpCookie{
    override val name: String
        get() = raw.name
    override val value: String
        get() =  raw.value
    override val comment: String?
        get() =  raw.comment
    override val domain: String?
        get() = raw.domain
    override val maxAge: Int
        get() = raw.maxAge
    override val path: String?
        get() = raw.path
    override val secure: Boolean
        get() = raw.secure
    override val version: Int
        get() = raw.version
    override val isHttpOnly: Boolean
        get() = raw.isHttpOnly

    companion object{
        fun HttpCookie.cast(): Cookie{
            return Cookie(name, value).also {
                it.comment = comment
                it.domain = domain
                it.maxAge = maxAge
                it.path = path
                it.secure = secure
                it.version = version
                it.isHttpOnly = isHttpOnly
            }
        }
    }
}

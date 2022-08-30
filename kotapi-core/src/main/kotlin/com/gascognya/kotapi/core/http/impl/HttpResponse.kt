package com.gascognya.kotapi.core.http.impl

import com.gascognya.kotapi.core.http.Response
import com.gascognya.kotapi.core.utils.ResponseBodyUtils
import jakarta.servlet.http.Cookie
import java.io.InputStream

class HttpResponse : Response {
    override var stream: InputStream = InputStream.nullInputStream()
    override var status: Int = 200
    override val headers: MutableMap<String, MutableList<String>> = mutableMapOf()
    override val cookies: MutableList<Cookie> = mutableListOf()

    override fun toString(): String {
        return ResponseBodyUtils.format(this)
    }
}
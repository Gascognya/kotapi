package com.gascognya.kotapi.core.http.impl

import com.gascognya.kotapi.core.http.Response
import jakarta.servlet.http.Cookie
import java.io.InputStream

class HttpResponse(override val stream: InputStream) : Response {
    override var status: Int = 200
    override val headers: MutableMap<String, MutableList<String>> = mutableMapOf()
    override val cookies: MutableList<Cookie> = mutableListOf()
}
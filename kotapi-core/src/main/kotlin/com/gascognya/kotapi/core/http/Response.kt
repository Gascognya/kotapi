package com.gascognya.kotapi.core.http

import jakarta.servlet.http.Cookie
import java.io.InputStream

interface Response {
    var status: Int
    val stream: InputStream
    val headers: Map<String, List<String>>
    val cookies: List<Cookie>
}
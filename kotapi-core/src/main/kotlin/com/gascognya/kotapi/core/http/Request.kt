package com.gascognya.kotapi.core.http

import com.gascognya.kotapi.core.utils.collection.PropertyMap
import jakarta.servlet.http.Cookie
import java.io.InputStream

interface Request {
    val path: String
    val method: HttpMethod
    val stream: InputStream

    val queryParams: Map<String, String>
    val pathParams: Map<String, String>


    val headers: Map<String, List<String>>
    val cookies: List<Cookie>

    val localAddress: Pair<String, Int>
    val remoteAddress: Pair<String, Int>

    val store: PropertyMap
}


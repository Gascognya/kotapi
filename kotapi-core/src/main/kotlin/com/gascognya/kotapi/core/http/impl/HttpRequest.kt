package com.gascognya.kotapi.core.http.impl

import com.gascognya.kotapi.core.http.HttpMethod
import com.gascognya.kotapi.core.http.Request
import com.gascognya.kotapi.core.utils.PathParamsKey
import com.gascognya.kotapi.core.utils.QueryStringParseUtils
import com.gascognya.kotapi.core.utils.collection.PropertyMap
import com.gascognya.kotapi.core.utils.collection.ServletHeadersProxyMap
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.Part
import java.io.InputStream

class HttpRequest(private val raw: HttpServletRequest): Request {
    override val path: String = raw.requestURI
    override val method: HttpMethod = HttpMethod.from(raw.method)

    override val queryParams: Map<String, String> by lazy { QueryStringParseUtils.parse(raw.queryString) }
    override val pathParams: Map<String, String>
        get() = store[PathParamsKey] ?: mapOf()
    override val headers: Map<String, List<String>> = ServletHeadersProxyMap(raw)
    override val cookies: List<Cookie> by lazy { raw.cookies.toList() }
    override val localAddress: Pair<String, Int> by lazy { raw.localName to raw.localPort }
    override val remoteAddress: Pair<String, Int> by lazy { raw.remoteHost to raw.remotePort }
    override val store: PropertyMap by lazy { PropertyMap() }

    override val stream: InputStream = raw.inputStream
}
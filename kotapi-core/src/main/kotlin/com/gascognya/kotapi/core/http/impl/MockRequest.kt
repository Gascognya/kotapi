package com.gascognya.kotapi.core.http.impl

import com.gascognya.kotapi.core.http.HttpCookie
import com.gascognya.kotapi.core.http.HttpMethod
import com.gascognya.kotapi.core.http.Request
import com.gascognya.kotapi.core.utils.PathParamsKey
import com.gascognya.kotapi.core.utils.collection.PropertyMap
import java.io.InputStream

class MockRequest: Request {
    override lateinit var path: String
    override lateinit var stream: InputStream
    override var method: HttpMethod = HttpMethod.None
    override val queryParams: MutableMap<String, String> = mutableMapOf()
    override val pathParams: Map<String, String>
        get() = store[PathParamsKey] ?: mapOf()
    override val headers: MutableMap<String, MutableList<String>> = mutableMapOf()
    override val cookies: MutableList<HttpCookie> = mutableListOf()
    override var localAddress: Pair<String, Int> = "127.0.0.1" to 8000
    override var remoteAddress: Pair<String, Int> = "127.0.0.1" to 9000
    override val store: PropertyMap = PropertyMap()

}
package com.gascognya.kotapi.core.utils.collection

import jakarta.servlet.http.HttpServletRequest
import java.util.Collections

class ServletHeadersProxyMap(private val request: HttpServletRequest): Map<String, List<String>> {
    private val cache = mutableMapOf<String, MutableList<String>>()

    private val keyList: List<String> = Collections.list(request.headerNames)

    override fun get(key: String): List<String>? {
        if (key in keyList){
            var values = cache[key]
            if (values == null){
                values = Collections.list(request.getHeaders(key))
                cache[key] = values
            }
            return values
        }
        return null
    }

    override val size: Int = keyList.size

    override val entries: Set<Map.Entry<String, List<String>>>
        get() = TODO("Not implemented")

    override val keys: Set<String> by lazy { keyList.toMutableSet() }
    override val values: Collection<List<String>>
        get() = TODO("Not implemented")

    override fun containsKey(key: String): Boolean = key in keyList

    override fun containsValue(value: List<String>): Boolean {
        TODO("Not implemented")
    }

    override fun isEmpty(): Boolean = keyList.isEmpty()

}
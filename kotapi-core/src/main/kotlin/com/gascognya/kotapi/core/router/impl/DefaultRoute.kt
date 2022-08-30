package com.gascognya.kotapi.core.router.impl

import com.gascognya.kotapi.core.Application
import com.gascognya.kotapi.core.http.HttpMethod
import com.gascognya.kotapi.core.http.Request
import com.gascognya.kotapi.core.router.Route
import com.gascognya.kotapi.core.utils.PathParamsKey
import com.gascognya.kotapi.core.utils.matcher.PathPattern
import com.gascognya.kotapi.standard.utils.matcher.PathContainer

class DefaultRoute(
    override val path: String,
    override val method: HttpMethod,
    private val app: Application
) : Route, Application by app {
    private val pattern = PathPattern.tryFrom(path)

    override fun match(request: Request): Boolean {
        if (request.method !in method) return false
        val container = request.store.get() ?: let {
            request.store.set(PathContainer.tryFrom(request.path))
        }
        val map = pattern.match(container) ?: return false
        request.store[PathParamsKey] = map
        return true
    }
}
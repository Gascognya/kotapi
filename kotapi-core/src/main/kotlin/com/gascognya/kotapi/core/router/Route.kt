package com.gascognya.kotapi.core.router

import com.gascognya.kotapi.core.Application
import com.gascognya.kotapi.core.Middleware
import com.gascognya.kotapi.core.http.HttpMethod
import com.gascognya.kotapi.core.http.Request
import com.gascognya.kotapi.core.router.impl.DefaultRoute

interface Route : Application {
    val path: String
    val method: HttpMethod
    fun match(request: Request): Boolean

    fun interface Constructor {
        operator fun invoke(prefix: String, middleware: Middleware?): Route
    }

    companion object {
        fun create(path: String, method: HttpMethod, app: Application) = Constructor { prefix, m ->
            DefaultRoute(prefix + path, method, m?.then(app) ?: app)
        }
    }
}
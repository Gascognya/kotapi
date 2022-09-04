package com.gascognya.kotapi.core.router

import com.gascognya.kotapi.core.Application
import com.gascognya.kotapi.core.Middleware
import com.gascognya.kotapi.core.http.HttpMethod
import com.gascognya.kotapi.core.http.Request

interface Route : Application {
    val path: String
    val method: HttpMethod
    fun match(request: Request): Boolean

    fun interface Constructor {
        operator fun invoke(prefix: String, middleware: Middleware?): Route
    }
}
package com.gascognya.kotapi.core

import com.gascognya.kotapi.core.http.Request
import com.gascognya.kotapi.core.http.Response

fun interface Application {
    operator fun invoke(request: Request): Response

    operator fun invoke(builder: Request.Builder): Response = invoke(builder.build())
}
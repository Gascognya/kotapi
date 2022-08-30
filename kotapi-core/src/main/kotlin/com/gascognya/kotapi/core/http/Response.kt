package com.gascognya.kotapi.core.http

import com.gascognya.kotapi.core.http.impl.HttpResponse
import com.gascognya.kotapi.core.utils.annotation.Fluent
import com.gascognya.kotapi.core.utils.collection.putMany
import com.gascognya.kotapi.core.utils.collection.putOne
import jakarta.servlet.http.Cookie
import java.io.InputStream

interface Response {
    var status: Int
    val stream: InputStream
    val headers: Map<String, List<String>>
    val cookies: List<Cookie>

    class Builder{
        private val resp = HttpResponse()

        @Fluent
        fun setStatus(code: Int) = apply { resp.status = code }

        @Fluent
        fun addHeader(name: String, value: String) = apply { resp.headers.putOne(name, value) }

        @Fluent
        fun addHeaders(name: String, vararg value: String) = apply { resp.headers.putMany(name, value.toList()) }

        @Fluent
        fun addCookie(cookie: Cookie) = apply { resp.cookies.add(cookie) }

        @Fluent
        fun setBody(body: String) = apply { resp.stream = body.byteInputStream() }

        @Fluent
        fun setBody(body: ByteArray) = apply { resp.stream = body.inputStream() }

        @Fluent
        fun setBody(body: InputStream) = apply { resp.stream = body }

        fun build(): Response = resp
    }

    companion object{
        operator fun invoke(body: String, status: Int = 200, contentType: String = "text/plain"): Response{
            return Builder()
                .setBody(body)
                .addHeader("content-type", contentType)
                .setStatus(status)
                .build()
        }

        operator fun invoke(body: InputStream, status: Int = 200, contentType: String = "application/octet-stream"): Response{
            return Builder()
                .setBody(body)
                .addHeader("content-type", contentType)
                .setStatus(status)
                .build()
        }
    }
}
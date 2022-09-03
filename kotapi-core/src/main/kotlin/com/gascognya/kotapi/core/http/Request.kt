@file:Suppress("FunctionName")

package com.gascognya.kotapi.core.http

import com.gascognya.kotapi.core.http.impl.MockRequest
import com.gascognya.kotapi.core.utils.annotation.Fluent
import com.gascognya.kotapi.core.utils.collection.PropertyMap
import com.gascognya.kotapi.core.utils.collection.putMany
import com.gascognya.kotapi.core.utils.collection.putOne
import java.io.InputStream

interface Request {
    val path: String
    val method: HttpMethod
    val stream: InputStream

    val queryParams: Map<String, String>
    val pathParams: Map<String, String>

    val headers: Map<String, List<String>>
    val cookies: List<HttpCookie>

    val localAddress: Pair<String, Int>
    val remoteAddress: Pair<String, Int>

    val store: PropertyMap

    class Builder(){
        private val req = MockRequest()
        @Fluent
        fun setPath(path: String) = apply { req.path = path }

        @Fluent
        fun setMethod(method: HttpMethod) = apply { req.method = method }

        @Fluent
        fun addQuery(name: String, value: String) = apply { req.queryParams[name] = value }

        @Fluent
        fun addHeader(name: String, value: String) = apply { req.headers.putOne(name, value) }

        @Fluent
        fun addHeaders(name: String, vararg value: String) = apply { req.headers.putMany(name, value.toList()) }

        @Fluent
        fun addCookie(cookie: HttpCookie) = apply { req.cookies.add(cookie) }

        @Fluent
        fun setRemoteAddress(host: String, port: Int) = apply { req.remoteAddress = host to port }

        @Fluent
        fun setLocalAddress(host: String, port: Int) = apply { req.localAddress = host to port }

        fun build(): Request = req
    }

    companion object{
        fun GET(path: String): Request.Builder {
            return Request.Builder().setPath(path).setMethod(HttpMethod.Get)
        }

        fun POST(path: String): Request.Builder {
            return Request.Builder().setPath(path).setMethod(HttpMethod.Post)
        }

        fun DELETE(path: String): Request.Builder {
            return Request.Builder().setPath(path).setMethod(HttpMethod.Delete)
        }

        fun PUT(path: String): Request.Builder {
            return Request.Builder().setPath(path).setMethod(HttpMethod.Put)
        }

        fun PATCH(path: String): Request.Builder {
            return Request.Builder().setPath(path).setMethod(HttpMethod.Patch)
        }
    }
}


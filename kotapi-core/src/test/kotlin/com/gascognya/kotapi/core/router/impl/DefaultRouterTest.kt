package com.gascognya.kotapi.core.router.impl

import com.gascognya.kotapi.core.Application
import com.gascognya.kotapi.core.Middleware
import com.gascognya.kotapi.core.exception.Catching
import com.gascognya.kotapi.core.exception.ExceptionCatchingMiddleware
import com.gascognya.kotapi.core.http.HttpMethod
import com.gascognya.kotapi.core.http.Request
import com.gascognya.kotapi.core.http.Response
import com.gascognya.kotapi.core.router.Router
import com.gascognya.kotapi.core.utils.ResponseBodyUtils
import org.slf4j.LoggerFactory
import kotlin.test.*

internal class DefaultRouterTest {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Test
    fun `simple test 1`() {
        val echoMiddleware = Middleware { next ->
            Application {
                logger.info("${it.method} ${it.path}")
                next(it)
            }
        }

        val router = Router.Builder()
            .middleware(echoMiddleware)
            .get("/hello/{name}") {
                val name = it.pathParams["name"] ?: "unknown"
                val age = it.queryParams["age"] ?: "-1"
                Response("hello $name ($age)")
            }
            .build()

        run {
            val request = Request.Builder()
                .setPath("/hello/bob")
                .setMethod(HttpMethod.Get)
                .addQuery("age", "18")
                .build()

            val response = router(request)
            val msg = ResponseBodyUtils.asText(response)
            assertEquals("hello bob (18)", msg)
        }
    }
}
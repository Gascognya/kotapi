package com.gascognya.kotapi.core.exception

import com.gascognya.kotapi.core.Application
import com.gascognya.kotapi.core.http.Request.Companion.GET
import com.gascognya.kotapi.core.http.Response
import org.slf4j.LoggerFactory
import kotlin.test.*

internal class ExceptionCatchingMiddlewareTest{
    private val logger = LoggerFactory.getLogger(javaClass)

    private fun Catching<RuntimeException>.handler(): Response {
        logger.warn(exception.message)
        return Response(exception.message ?: "unknown", 500)
    }

    @Test
    fun `simple test 1`(){
        val app = Application{
            throw RuntimeException("some error")
        }

        val middleware = ExceptionCatchingMiddleware
            .Builder()
            .add<RuntimeException> { handler() }
            .build()

        val newApp = middleware.then(app)

        val request = GET("/world")

        val response = newApp(request)

        println(response)
        assertEquals(500, response.status)
    }

}
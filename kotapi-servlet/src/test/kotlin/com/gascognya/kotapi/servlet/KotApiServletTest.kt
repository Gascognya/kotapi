package com.gascognya.kotapi.servlet

import com.gascognya.kotapi.core.Application
import com.gascognya.kotapi.core.http.Response
import org.eclipse.jetty.client.HttpClient
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import kotlin.test.*

internal class KotApiServletTest{

    private fun startServer(app: Application, port: Int = 8080){
        val holder = ServletHolder(KotApiServlet(app))

        val handler = ServletContextHandler()
        handler.addServlet(holder, "/")
        val server = Server(port)
        server.handler = handler
        server.start()
    }

    @Test
    fun `server start test 1`(){
        val app = Application {
            val name = it.queryParams["name"] ?: "unknown"
            Response("hello $name")
        }

        startServer(app)

        HttpClient().apply {
            start()
            val response = GET("http://127.0.0.1:8080?name=bob")
            assertEquals("hello bob", response.contentAsString)
            stop()
        }
    }
}
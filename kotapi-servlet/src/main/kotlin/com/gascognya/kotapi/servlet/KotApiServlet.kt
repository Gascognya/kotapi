package com.gascognya.kotapi.servlet

import com.gascognya.kotapi.core.Application
import com.gascognya.kotapi.core.http.Response
import com.gascognya.kotapi.servlet.ServletCookie.Companion.cast
import jakarta.servlet.GenericServlet
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class KotApiServlet(private val app: Application): GenericServlet() {
    override fun service(rawReq: ServletRequest, rawResp: ServletResponse) {
        if (rawReq !is HttpServletRequest || rawResp !is HttpServletResponse) {
            throw ServletException("non-HTTP request or response")
        }
        val request = ServletRequest(rawReq)
        val response = app(request)
        convertResponse(response, rawResp)
    }

    private fun convertResponse(response: Response, raw: HttpServletResponse){
        raw.status = response.status
        for (header in response.headers) {
            for (value in header.value) {
                raw.addHeader(header.key, value)
            }
        }

        for (cookie in response.cookies) {
            raw.addCookie(cookie.cast())
        }

        response.stream.copyTo(raw.outputStream)
    }
}
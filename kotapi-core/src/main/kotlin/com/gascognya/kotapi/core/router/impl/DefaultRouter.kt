package com.gascognya.kotapi.core.router.impl

import com.gascognya.kotapi.core.http.Request
import com.gascognya.kotapi.core.http.Response
import com.gascognya.kotapi.core.router.Route
import com.gascognya.kotapi.core.router.Router
import com.gascognya.kotapi.core.utils.exception.RequestNotMatchException

class DefaultRouter(private val routes: List<Route>): Router {
    override fun invoke(request: Request): Response {
        var response: Response? = null
        for (route in routes) {
            if (route.match(request)){
                request.store.set(route)
                response = route(request)
            }
        }
        return response ?: throw RequestNotMatchException(request)
    }
}
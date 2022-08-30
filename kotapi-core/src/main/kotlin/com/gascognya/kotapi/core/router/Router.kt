package com.gascognya.kotapi.core.router

import com.gascognya.kotapi.core.Application
import com.gascognya.kotapi.core.Middleware
import com.gascognya.kotapi.core.http.HttpMethod
import com.gascognya.kotapi.core.router.impl.DefaultRoute
import com.gascognya.kotapi.core.router.impl.DefaultRouter
import com.gascognya.kotapi.core.utils.annotation.Fluent

interface Router : Application {

    class Builder {
        private val routeConstructors = mutableListOf<Route.Constructor>()
        private val middlewares = mutableListOf<Middleware>()
        private var prefix: String = ""

        @Fluent
        fun setPrefix(prefix: String) = apply {
            this.prefix = prefix
        }

        @Fluent
        fun middleware(middleware: Middleware) = apply {
            middlewares.add(middleware)
        }

        @Fluent
        fun route(route: Route.Constructor) = apply {
            routeConstructors.add(route)
        }

        @Fluent
        fun route(path: String, method: HttpMethod, app: Application) = apply {
            route(Route.create(path, method, app))
        }
        
        @Fluent
        fun get(path: String, app: Application) = route(path, HttpMethod.Get, app)

        @Fluent
        fun post(path: String, app: Application) = route(path, HttpMethod.Post, app)

        @Fluent
        fun delete(path: String, app: Application) = route(path, HttpMethod.Delete, app)

        @Fluent
        fun put(path: String, app: Application) = route(path, HttpMethod.Put, app)

        @Fluent
        fun patch(path: String, app: Application) = route(path, HttpMethod.Patch, app)


        fun build(): Router {
            val middleware = linkedMiddleware()
            val routes = routeConstructors.map { it(prefix, middleware) }
            return DefaultRouter(routes)
        }
        
        private fun linkedMiddleware(): Middleware?{
            var target: Middleware? = null
            for (next in middlewares) {
                target = target?.then(next) ?: next
            }
            return target
        }
    }
}
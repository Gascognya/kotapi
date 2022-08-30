package com.gascognya.kotapi.core.exception

import com.gascognya.kotapi.core.Application
import com.gascognya.kotapi.core.Middleware
import com.gascognya.kotapi.core.http.Request
import com.gascognya.kotapi.core.http.Response
import com.gascognya.kotapi.core.utils.annotation.Fluent
import org.slf4j.LoggerFactory

class ExceptionCatchingMiddleware(
    private val next: Application,
    private val handlers: MutableMap<Class<*>, (Request, Throwable) -> Response>
) : Application {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun invoke(request: Request): Response {
        return try {
            next(request)
        } catch (t: Throwable) {
            val cls = t::class.java
            var handler = handlers[cls]

            if (handler == null) {
                synchronized(this) {
                    handler = handlers[cls]
                    if (handler == null) {
                        var superclass = cls.superclass
                        while (handler == null && superclass != Any::class.java) {
                            handler = handlers[superclass]
                            superclass = superclass.superclass
                        }

                        if (handler == null) handler = defaultHandler

                        handlers[cls] = handler!!
                    }
                }
            }
            handler!!.invoke(request, t)
        }
    }

    private val defaultHandler: (Request, Throwable) -> Response = { request, throwable ->
        val msg = throwable.message ?: throwable.javaClass.simpleName
        logger.warn(msg)
        Response(msg, 500)
    }

    class Builder{
        private val handlers: MutableMap<Class<*>, (Request, Throwable) -> Response> = mutableMapOf()

        @Fluent
        fun <T : Throwable> add(cls: Class<T>, handler: Catching<T>.() -> Response) = apply {
            handlers[cls] = { request, throwable -> handler(Catching(request, throwable)) }
        }

        @Fluent
        fun <T : Throwable> add(cls: Class<T>, handler: (Request, Throwable) -> Response) = apply {
            handlers[cls] = handler
        }

        @Fluent
        inline fun <reified T : Throwable> add(noinline handler: Catching<T>.() -> Response): Builder {
            return add(T::class.java, handler)
        }

        fun build(): Middleware {
            val newMap = mutableMapOf<Class<*>, (Request, Throwable) -> Response>()
            newMap.putAll(handlers)
            return Middleware { ExceptionCatchingMiddleware(it, newMap) }
        }
    }
}

data class Catching<T : Throwable>(val request: Request, val exception: Throwable)
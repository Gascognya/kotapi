package com.gascognya.kotapi.core

fun interface Middleware {
    fun then(app: Application): Application
    fun then(next: Middleware): Middleware = Middleware { then(next.then(it)) }
}
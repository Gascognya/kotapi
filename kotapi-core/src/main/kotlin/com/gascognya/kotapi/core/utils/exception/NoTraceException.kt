package com.gascognya.kotapi.core.utils.exception

open class NoTraceException(msg: String): Throwable(msg, null, false, false){
    override fun toString(): String {
        return "Exception: ${this.javaClass.simpleName}\nMessage: $message"
    }
}

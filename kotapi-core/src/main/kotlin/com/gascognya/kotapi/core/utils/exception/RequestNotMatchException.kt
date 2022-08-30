package com.gascognya.kotapi.core.utils.exception

import com.gascognya.kotapi.core.http.Request
import com.gascognya.kotapi.core.utils.exception.NoTraceException

class RequestNotMatchException(request: Request): NoTraceException("${request.method} ${request.path}") {
}
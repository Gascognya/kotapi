package com.gascognya.kotapi.standard.utils.matcher

import com.gascognya.kotapi.core.utils.exception.NoTraceException

class PathParseException(path: String): NoTraceException(path)
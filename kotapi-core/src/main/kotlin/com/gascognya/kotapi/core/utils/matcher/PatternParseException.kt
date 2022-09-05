package com.gascognya.kotapi.core.utils.matcher

import com.gascognya.kotapi.core.utils.exception.NoTraceException

class PatternParseException(pattern: String): NoTraceException(pattern)
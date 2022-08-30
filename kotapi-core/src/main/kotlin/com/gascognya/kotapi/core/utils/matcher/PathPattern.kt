package com.gascognya.kotapi.core.utils.matcher

import com.gascognya.kotapi.standard.utils.matcher.PathContainer
import com.gascognya.kotapi.standard.utils.matcher.PathSegment
import com.gascognya.kotapi.standard.utils.matcher.PatternParseException


class PathPattern private constructor(private val segments: List<PathSegment>) {
    fun match(container: PathContainer): Map<String, String>? {
        val elements = container.elements.iterator()

        var variable: MutableMap<String, String>? = null

        for (segment in segments) {
            when (segment) {
                PathSegment.Remain -> {
                    if (!elements.hasNext()) return null
                    return variable ?: mapOf()
                }

                is PathSegment.Literal -> {
                    if (!elements.hasNext()) return null
                    if (segment.value != elements.next()) return null
                }

                is PathSegment.Variable -> {
                    if (!elements.hasNext()) return null
                    val element = elements.next()
                    if (segment.regex != null) {
                        if (!segment.regex.matches(element)) return null
                    }
                    if (segment.name != null) {
                        if (variable == null) variable = mutableMapOf()
                        variable[segment.name] = element
                    }
                }
            }
        }
        if (elements.hasNext()) return null

        return variable ?: mapOf()
    }

    companion object {
        private val EmptyPattern = PathPattern(listOf())

        fun from(path: String): PathPattern? {
            if (path == "/" || path == "") return EmptyPattern

            val elements = path.trim('/', ' ').split('/')

            val iterator = elements.iterator()

            val segments = mutableListOf<PathSegment>()

            while (iterator.hasNext()) {
                val rawSeg = iterator.next()
                val segment = PathSegment.from(rawSeg) ?: return null
                if (segment is PathSegment.Remain) {
                    if (iterator.hasNext()) return null
                }
                segments.add(segment)
            }

            return PathPattern(segments)
        }

        fun tryFrom(path: String): PathPattern = from(path) ?: throw PatternParseException(path)
    }
}
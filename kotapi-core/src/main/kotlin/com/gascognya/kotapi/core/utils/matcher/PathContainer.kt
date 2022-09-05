package com.gascognya.kotapi.core.utils.matcher

class PathContainer(val elements: List<String>) {

    companion object {
        private val EmptyPathContainer = PathContainer(listOf())

        fun from(path: String): PathContainer? {
            if (path == "/" || path == "") return EmptyPathContainer

            val elements = path.trim('/', ' ').split('/')
            if (!elements.all(PathSegment.Companion::checkSpecialCharacters)) {
                return null
            }
            return PathContainer(elements)
        }

        fun tryFrom(path: String): PathContainer = from(path) ?: throw PathParseException(path)
    }
}
@file:Suppress("VARIABLE_WITH_REDUNDANT_INITIALIZER")

package com.gascognya.kotapi.core.utils



object QueryStringParseUtils {
    fun parse(queryString: String): Map<String, String> {
        var next = true
        var key = ""
        var value = ""
        val element = StringBuilder()

        val map = mutableMapOf<String, String>()

        for (c in queryString) {
            if (next){
                if (c == '='){
                    key = element.toString()
                    element.clear()
                    next = false
                    continue
                }
                if (c == '&'){
                    throw IllegalArgumentException("query parameter")
                }
            } else {
                if (c == '&'){
                    value = element.toString()
                    element.clear()
                    map[key] = value
                    next = true
                    continue
                }
                if (c == '='){
                    throw IllegalArgumentException("query parameter")
                }
            }
            element.append(c)
        }
        if (element.isNotEmpty()){
            map[key] = element.toString()
        }

        return map
    }
}
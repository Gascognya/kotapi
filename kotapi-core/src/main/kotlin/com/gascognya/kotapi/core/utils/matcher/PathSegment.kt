package com.gascognya.kotapi.core.utils.matcher

internal sealed interface PathSegment {
    class Literal(val value: String) : PathSegment
    class Variable(val name: String? = null, val regex: Regex? = null) : PathSegment
    object Remain : PathSegment

    companion object{
        private val regexExp = Regex("""\{(.*):(.+)}""")
        private val normalExp = Regex("""\{(.*)}""")
        private val specialCharacters = listOf('+', ' ', '/', '?', '%', '#', '&', '=')

        fun checkSpecialCharacters(rawSeg: String): Boolean{
            for (c in rawSeg) {
                if (c in specialCharacters) return false
            }
            return true
        }

        fun from(element: String): PathSegment? {
            if (element == "**") return Remain

            if (element == "*") return Variable()

            val res1 = regexExp.matchEntire(element)
            if (res1 != null){
                val name = res1.groupValues[1].ifBlank { null }
                val regex = res1.groupValues[2].ifBlank { null }?.let { Regex(it) }
                return Variable(name, regex)
            }

            val res2 = normalExp.matchEntire(element)
            if (res2 != null){
                val name = res2.groupValues[1].ifBlank { null }
                return Variable(name)
            }

            if (!checkSpecialCharacters(element)) return null

            return Literal(element)
        }
    }
}
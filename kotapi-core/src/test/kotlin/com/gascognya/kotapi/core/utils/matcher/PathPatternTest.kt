package com.gascognya.kotapi.core.utils.matcher

import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.*

internal class PathPatternTest {
    @Test
    fun `simple test 1`() {
        createPattern("/hello").apply {
            assertMatch("/hello")
            assertMatch("hello")
            assertMatch("hello/")
            assertMatch("/hello/")
        }
    }

    @Test
    fun `param test 1`() {
        createPattern("/hello/{name}").apply {
            assertMatch("/hello/bob").assertEquals("bob", "name")
        }
    }

    private fun Map<String, String>.assertNotContains(key: String): Map<String, String> {
        assertNull(get(key))
        return this
    }

    private fun Map<String, String>.assertContains(key: String): Map<String, String> {
        assertNotNull(get(key))
        return this
    }

    private fun Map<String, String>.assertNotEquals(expected: String, key: String): Map<String, String> {
        assertNotEquals(expected, get(key))
        return this
    }

    private fun Map<String, String>.assertEquals(expected: String, key: String): Map<String, String> {
        assertEquals(expected, get(key))
        return this
    }

    private fun createPattern(pattern: String): PathPattern {
        return assertDoesNotThrow("illegal pattern : $pattern") { PathPattern.tryFrom(pattern) }
    }

    private fun createContainer(path: String): PathContainer {
        return assertDoesNotThrow("illegal path : $path") { PathContainer.tryFrom(path) }
    }

    private fun PathPattern.assertMatch(path: String): Map<String, String> {
        val container = createContainer(path)
        return assertNotNull(match(container), "path: $path not match")
    }

    private fun PathPattern.assertNotMatch(path: String) {
        val container = createContainer(path)
        assertNull(match(container), "path: $path is matched")
    }
}
@file:Suppress("ReplaceGetOrSet", "UNCHECKED_CAST")

package com.gascognya.kotapi.core.utils.collection

import kotlin.reflect.KClass

class PropertyKey<T : Any>(val name: String?, val cls: KClass<T>) {
    companion object{
        inline operator fun <reified T : Any> invoke(name: String? = null): PropertyKey<T> = PropertyKey(name, T::class)
        internal fun String.internalName(): String = """&_*_${this}"""
    }
}

sealed class ReadonlyPropertyMap {
    protected val store = mutableMapOf<String?, MutableMap<KClass<*>, Any>>()

    operator fun <T : Any> get(name: String?, cls: KClass<T>): T? = store.get(name)?.get(cls) as? T

    operator fun <T : Any> get(key: PropertyKey<T>): T? = get(key.name, key.cls)

    inline fun <reified T : Any> get(): T? = get(null, T::class)
}

class PropertyMap : ReadonlyPropertyMap() {

    operator fun <T : Any> set(name: String?, cls: KClass<T>, value: T): T {
        val map = store.get(name) ?: store.fluentSet(name, mutableMapOf())
        map.set(cls, value)
        return value
    }

    operator fun <T : Any> set(key: PropertyKey<T>, value: T): T {
        return set(key.name, key.cls, value)
    }

    inline fun <reified T : Any> set(value: T): T {
        return set(null, T::class, value)
    }

    companion object {
        private fun <K, V> MutableMap<K, V>.fluentSet(key: K, value: V): V = value.also { set(key, it) }
    }
}
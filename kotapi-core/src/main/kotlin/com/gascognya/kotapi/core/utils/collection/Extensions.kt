package com.gascognya.kotapi.core.utils.collection

fun <K, V> MutableMap<K, MutableList<V>>.putOne(key: K, value: V) {
    var list = get(key)
    if (list == null) {
        list = mutableListOf(value)
        put(key, list)
    } else {
        list.add(value)
    }
}

fun <K, V> MutableMap<K, MutableList<V>>.putMany(key: K, values: List<V>) {
    val list = get(key)
    if (list == null) {
        put(key, values.toMutableList())
    } else {
        list.addAll(values)
    }
}
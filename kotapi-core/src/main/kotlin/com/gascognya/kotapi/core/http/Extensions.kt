package com.gascognya.kotapi.core.http

import com.gascognya.kotapi.core.utils.PrimitiveTypeCastUtils

inline fun <reified T : Any> Request.queryParam(name: String): T?{
    val value = queryParams[name] ?: return null
    return PrimitiveTypeCastUtils.strictConvert(value, T::class)
}

inline fun <reified T : Any> Request.queryParam(name: String, default: T): T{
    return queryParam(name) ?: default
}

inline fun <reified T : Any> Request.pathParam(name: String): T?{
    val value = pathParams[name] ?: return null
    return PrimitiveTypeCastUtils.strictConvert(value, T::class)
}

inline fun <reified T : Any> Request.pathParam(name: String, default: T): T{
    return pathParam(name) ?: default
}
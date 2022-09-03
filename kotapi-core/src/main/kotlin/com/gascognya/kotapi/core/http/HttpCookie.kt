package com.gascognya.kotapi.core.http

interface HttpCookie {
    val name: String
    val value: String

    val comment: String?
    val domain: String?
    val maxAge: Int
    val path: String?
    val secure: Boolean
    val version: Int
    val isHttpOnly: Boolean
}
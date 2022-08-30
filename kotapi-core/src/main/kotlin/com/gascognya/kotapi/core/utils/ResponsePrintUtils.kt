package com.gascognya.kotapi.core.utils

import com.gascognya.kotapi.core.http.Response
import java.nio.charset.Charset

object ResponsePrintUtils {

    fun format(response: Response): String {
        val headerString = response.headers
            .map { "${it.key}: ${it.value.joinToString("; ")}" }
            .joinToString("\n|")

        val bodyString = response.stream
            .readAllBytes()
            .toString(Charset.defaultCharset()).replace("\n", "\n|")
            .chunked(72)
            .joinToString("\n|")

        return "\n|------------------------------------------------------------------------\n" +
                "|Response ${response.status}\n" +
                "|-------------------------------- Header --------------------------------\n" +
                "|$headerString\n" +
                "|--------------------------------  Body  --------------------------------\n" +
                "|$bodyString\n" +
                "|------------------------------------------------------------------------\n"
    }
}
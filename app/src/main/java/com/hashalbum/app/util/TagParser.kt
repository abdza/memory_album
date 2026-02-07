package com.hashalbum.app.util

object TagParser {
    fun parse(input: String): List<String> {
        return input.split(Regex("[\\s,]+"))
            .map { it.trimStart('#').lowercase().trim() }
            .filter { it.isNotEmpty() }
            .distinct()
    }
}

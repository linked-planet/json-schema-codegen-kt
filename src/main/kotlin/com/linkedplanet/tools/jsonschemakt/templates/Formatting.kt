package com.linkedplanet.tools.jsonschemakt.templates

fun indent(n: Int): String = "    ".repeat(n)
fun String.capitalize(): String = replaceFirstChar { it.uppercase() }

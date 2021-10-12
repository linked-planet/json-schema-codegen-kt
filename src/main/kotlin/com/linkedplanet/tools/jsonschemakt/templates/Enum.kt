package com.linkedplanet.tools.jsonschemakt.templates

import com.linkedplanet.tools.jsonschemakt.model.Property
import org.intellij.lang.annotations.Language

@Language("kotlin")
fun enumTemplate(name: String, prop: Property): String =
    """
sealed class ${name.capitalize()} {
${prop.enum!!.joinToString("\n", "", "") {
        "${indent(1)}object ${it.capitalize()} : ${name.capitalize()}()"
}}
${indent(1)}companion object {
${indent(2)}fun fromString(s: String): ${name.capitalize()}? =
${indent(3)}when(s.lowercase()) {
${prop.enum.joinToString("\n", "", "") {
        "${indent(4)}\"${it.lowercase()}\" -> ${it.capitalize()}"
}}
${indent(4)}else -> null
${indent(3)}}
${indent(1)}}
}
    """.trimIndent()


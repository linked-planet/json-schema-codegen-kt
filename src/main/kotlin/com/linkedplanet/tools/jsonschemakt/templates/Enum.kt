package com.linkedplanet.tools.jsonschemakt.templates

import com.linkedplanet.tools.jsonschemakt.model.Property
import org.intellij.lang.annotations.Language

@Language("kotlin")
fun enumTemplate(baseIndent: String, name: String, prop: Property): String =
    """${baseIndent}sealed class ${name.capitalize()} {
${prop.enum!!.joinToString("\n", "", "") {
        "${baseIndent}${indent(1)}${it.capitalize()} : ${name.capitalize()}()"
}}
${baseIndent}${indent(1)}companion object {
${baseIndent}${indent(2)}fromString(s: String): ${name.capitalize()}? =
${baseIndent}${indent(3)}when(s.lowercase()) {
${prop.enum.joinToString("\n", "", "") {
        "${baseIndent}${indent(4)}\"${it.lowercase()}\" -> ${it.capitalize()}"
}}
${baseIndent}${indent(4)}else -> null
${baseIndent}${indent(3)}}
${baseIndent}${indent(1)}}
${baseIndent}}
    """.trimIndent()


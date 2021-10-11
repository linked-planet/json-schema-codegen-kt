package com.linkedplanet.tools.jsonschemakt.templates

import com.linkedplanet.tools.jsonschemakt.model.Schema
import org.intellij.lang.annotations.Language

@Language("kotlin")
fun schemaTemplate(schema: Schema): String = """
package ${schema.id.toPackage()}

import java.math.BigDecimal
import org.joda.time.DateTime

${objectTemplate("", schema.title, schema.properties, schema.required)}
""".trimIndent()


fun String.toPackage(): String =
    "[:]([0-9])".toRegex()
        .replace(this) { ":_${it.groupValues[1]}" }
        .replace("-", "")
        .replace(".", "")
        .split(":")
        .dropLast(1)
        .joinToString(".")
        .lowercase()

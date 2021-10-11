package com.linkedplanet.tools.jsonschemakt.templates

import com.linkedplanet.tools.jsonschemakt.model.Property
import org.intellij.lang.annotations.Language

@Language("kotlin")
fun objectTemplate(baseIndent: String, name: String, properties: Map<String, Property>, required: List<String>): String = """
${baseIndent}data class ${name.capitalize()}(
${properties.map { (propName, prop) ->
    "${baseIndent}${indent(1)}val $propName: ${prop.propTypeToKotlinType(propName)}${if(!required.contains(propName)) { "?" } else { "" } }"
}.joinToString(",\n","","")}
${baseIndent})

${baseIndent}${properties.generateChildTypes(baseIndent).joinToString("\n\n","","")}
""".trimIndent()

fun Property.propTypeToKotlinType(name: String): String =
    when(this.type) {
        "string" -> when(this.format) {
            "date-time" -> "DateTime"
            else -> when {
                !enum.isNullOrEmpty() -> name.capitalize() // Enums shall be treated as sealed classes
                else -> "String"
            }
        }
        "number" -> "BigDecimal"
        "boolean" -> "Boolean"
        "array" -> "List<${items!!.type}>"
        "object" -> name.capitalize() // requires recursive generation
        else -> "Any"
    }

fun Map<String,Property>.generateChildTypes(baseIndent: String): List<String> =
    mapNotNull { (name, prop) ->
        if(prop.type == "object")
            objectTemplate(baseIndent,name, prop.properties!!, prop.required!!)
        else if (prop.enum != null) {
            enumTemplate(baseIndent, name, prop)
        } else null
    }


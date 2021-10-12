package com.linkedplanet.tools.jsonschemakt.templates

import com.linkedplanet.tools.jsonschemakt.model.Property
import org.intellij.lang.annotations.Language

@Language("kotlin")
fun objectTemplate(name: String, properties: Map<String, Property>, required: List<String>): String = """
data class ${name.capitalize()}(
${properties.map { (propName, prop) ->
    prop.generatePropLine(propName, required)
}.joinToString(",\n") { indent(1) + it } }
) {
${generateSizeConstraintFunction(indent(1), name)}
${generateSizeConstraintMap(name, properties)}
}

${properties.generateChildTypes().joinToString("\n\n","","")}
""".trimIndent()

@Language("kotlin")
fun Property.generatePropLine(propName: String, required: List<String>): String =
    "val $propName: ${propTypeToKotlinType(propName)}${if(!required.contains(propName)) { "?" } else { "" } }"

@Language("kotlin")
fun generateSizeConstraintFunction(indent: String, clsName: String): String =
    """
${indent}fun checkSizeConstraints(): Boolean =
${indent + indent(1)}${clsName}.sizeConstraints.all { (prop, size) ->
${indent + indent(2)}prop.getter.call(this).sizeOf() <= size
${indent + indent(1)}}
    """

@Language("kotlin")
fun generateSizeConstraintMap(clsName: String, properties: Map<String, Property>): String =
    """
    companion object {
        const val sizeConstraints = mapOf(
${properties.mapNotNull { (k, p) -> p.generateSizeConstraintEntry(clsName, k) }.joinToString(",\n") { indent(3) + it } }
        )
    }
    """

@Language("kotlin")
fun Property.generateSizeConstraintEntry(clsName: String, propName: String): String? =
    maxLength?.let { "${clsName}::${propName} to $it" }

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

fun Map<String,Property>.generateChildTypes(): List<String> =
    mapNotNull { (name, prop) ->
        if(prop.type == "object")
            objectTemplate(name, prop.properties!!, prop.required!!)
        else if (prop.enum != null) {
            enumTemplate(name, prop)
        } else null
    }


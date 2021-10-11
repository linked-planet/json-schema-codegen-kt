package com.linkedplanet.tools.jsonschemakt.model

data class Property(
    val type: String,
    val properties: Map<String,Property>?,
    val description: String?,
    val format: String?,
    val enum: List<String>?,
    val required: List<String>?,
    val items: Property?,
    val additionalProperties: Boolean,
    val maxLength: Int?
)

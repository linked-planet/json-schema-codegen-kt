package com.linkedplanet.tools.jsonschemakt.model

data class Schema(
    val `$schema`: String,
    val id: String,
    val title: String,
    val type: String,
    val properties: Map<String, Property>,
    val additionalProperties: Boolean,
    val required: List<String>?
)

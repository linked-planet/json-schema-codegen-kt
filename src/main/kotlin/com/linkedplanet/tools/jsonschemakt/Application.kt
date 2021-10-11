package com.linkedplanet.tools.jsonschemakt

import com.google.gson.Gson
import com.linkedplanet.tools.jsonschemakt.model.Schema
import com.linkedplanet.tools.jsonschemakt.templates.*
import org.intellij.lang.annotations.Language




val example: String = """
{
    "${"$"}schema": "http://json-schema.org/draft-04/schema#",
    "id": "urn:OCPP:1.6:2019:12:BootNotificationRequest",
    "title": "BootNotificationRequest",
    "type": "object",
    "properties": {
        "chargePointVendor": {
            "type": "string",
            "maxLength": 20
        },
        "chargePointModel": {
            "type": "string",
            "maxLength": 20
        },
        "chargePointSerialNumber": {
            "type": "string",
            "maxLength": 25
        },
        "chargeBoxSerialNumber": {
            "type": "string",
            "format": "date-time"
        },
        "firmwareVersion": {
            "type": "string",
            "maxLength": 50
        },
        "iccid": {
            "type": "string",
            "maxLength": 20
        },
        "imsi": {
            "type": "string",
            "maxLength": 20
        },
        "meterType": {
            "type": "string",
            "enum": [
                "Accepted",
                "Blocked",
                "Expired",
                "Invalid",
                "ConcurrentTx"
            ]
        },
        "meterSerialNumber": {
            "type": "string",
            "maxLength": 25
        },
        "items": {
            "type": "object",
            "properties": {
                "key": {
                    "type": "string",
                    "maxLength": 50
                },
                "readonly": {
                    "type": "boolean"
                },
                "value": {
                    "type": "string",
                    "maxLength": 500
                }
            },
            "additionalProperties": false,
            "required": [
                "key",
                "readonly"
            ]
        }
    },
    "additionalProperties": false,
    "required": [
        "chargePointVendor",
        "chargePointModel"
    ]
}
""".trimIndent()

fun main() {
    val result = Gson().fromJson(example, Schema::class.java)
    println(schemaTemplate(result))
}
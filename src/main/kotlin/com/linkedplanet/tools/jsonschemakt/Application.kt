package com.linkedplanet.tools.jsonschemakt

import com.google.gson.Gson
import com.linkedplanet.tools.jsonschemakt.model.Schema
import com.linkedplanet.tools.jsonschemakt.templates.*
import java.io.File

fun main(args: Array<String>) {
    val arguments = parseArgs(args.toList())
    arguments.sourceFiles?.onEach { fileName ->
        val reader = File("${arguments.sourceFolder ?: ""}$fileName").inputStream().reader()
        val result = Gson().fromJson(reader, Schema::class.java)
        val oFile = File("${arguments.targetPath ?: ""}${result.title}.kt").outputStream()
        oFile.write(schemaTemplate(result).toByteArray())
    }
}

fun parseArgs(args: List<String>): AppArgs =
    when (args[0]) {
        "-t" -> AppArgs(args[1], null, null) + parseArgs(args.drop(2))
        "-s" -> AppArgs(null, args[1], null) + parseArgs(args.drop(2))
        else -> AppArgs(null, null, args)
    }

data class AppArgs(
    val targetPath: String?,
    val sourceFolder: String?,
    val sourceFiles: List<String>?
) {
    operator fun plus(b: AppArgs):AppArgs =
        AppArgs(
            targetPath ?: b.targetPath,
            sourceFolder ?: b.sourceFolder,
            sourceFiles ?: b.sourceFiles
        )
}
package com.linkedplanet.tools.jsonschemakt

import com.google.gson.Gson
import com.linkedplanet.tools.jsonschemakt.model.Schema
import com.linkedplanet.tools.jsonschemakt.templates.*
import java.io.File

class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arguments = parseArgs(args.toList())
            arguments.sourceFiles?.onEach { fileName ->
                File("${arguments.sourceFolder ?: ""}$fileName").process(arguments.targetPath)
            } ?: arguments.sourceFolder?.let { folderName ->
                File(folderName).walk()
                    .filter { it.isFile && it.name.endsWith(".json") }
                    .forEach { it.process(arguments.targetPath) }
            }
        }

        fun File.process(targetPath: String?): Unit {
            val reader = inputStream().reader()
            val result = Gson().fromJson(reader, Schema::class.java)
            val dir = File("${targetPath ?: ""}/${result.id.toPackage().replace(".", "/")}")
            dir.mkdirs()
            val oFile = File("${dir.path}/${result.title}.kt").outputStream()
            oFile.write(schemaTemplate(result).toByteArray())
        }

        fun parseArgs(args: List<String>): AppArgs =
            if (args.isNotEmpty()) {
                when (args[0]) {
                    "-t" -> AppArgs(args[1], null, null) + parseArgs(args.drop(2))
                    "-s" -> AppArgs(null, args[1], null) + parseArgs(args.drop(2))
                    else -> AppArgs(null, null, args)
                }
            } else {
                AppArgs(null, null, null)
            }

        data class AppArgs(
            val targetPath: String?,
            val sourceFolder: String?,
            val sourceFiles: List<String>?
        ) {
            operator fun plus(b: AppArgs): AppArgs =
                AppArgs(
                    targetPath ?: b.targetPath,
                    sourceFolder ?: b.sourceFolder,
                    sourceFiles ?: b.sourceFiles
                )
        }
    }
}
package fstpl.fstpl.tasks

import com.beust.klaxon.JsonObject
import freemarker.template.Template
import fstpl.fstpl.TemplateResolver
import fstpl.util.cp
import fstpl.util.extensions.sortTask
import fstpl.util.extensions.sub
import fstpl.util.mkdir
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.streams.toList


class Copy(model: JsonObject, file: Path, outPath: Path, call: String, resolve: Boolean) :
    Task(model, file, outPath, call, resolve) {

    override fun execute(): List<Task> {
        return if (file.isDirectory()) executeDirectory() else executeFile()
    }

    private fun executeDirectory(): List<Task> {
        mkdir(outPath.sub(call))
        return Files.list(file).map { x -> x.sortTask(model, outPath.sub(call)) }.toList()
    }

    private fun executeFile(): List<Task> {
        if (resolve)
            resolve()
        else
            cp(file, outPath.sub(call))

        return listOf()
    }

    private fun resolve() {
        val outPath = outPath.sub(call)
        val relativeFile: Path = TemplateResolver.tplRoot.relativize(file)

        // Get relative path within the template directory
        val template: Template = TemplateResolver.engine.getTemplate(relativeFile.toString())

        //Create empty file and parent dir to write into
        val pathAsFile = outPath.toFile()
        pathAsFile.parentFile.mkdirs()
        pathAsFile.createNewFile()

        FileOutputStream(outPath.toString()).use {
            val writer = OutputStreamWriter(it)
            template.process(model, writer)
        }
    }

}
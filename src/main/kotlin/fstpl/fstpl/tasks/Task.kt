package fstpl.fstpl.tasks

import com.beust.klaxon.JsonObject
import freemarker.template.Template
import fstpl.fstpl.TemplateResolver
import fstpl.fstpl.parseTask
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.name
import kotlin.streams.toList


sealed class Task(protected val model: JsonObject, protected val file: Path, protected val outBase: Path) {

    fun execute(): List<Task> {
        resolveSelf()
        return collectSubTasks()
    }

    protected abstract fun resolveSelf()

    protected open fun collectSubTasks(): List<Task> {
        return Files.list(file).map { x -> x.parseTask(model, outBase) }.toList()
    }

    protected fun resolve(file: Path, outPath: Path) {
        // Get relative path within the template directory
        val template: Template = TemplateResolver.engine.getTemplate(file.toString())

        //Create empty file and parent dir to write into
        val pathAsFile = outPath.toFile()
        pathAsFile.parentFile.mkdirs()
        pathAsFile.createNewFile()

        FileOutputStream(outPath.toString()).use {
            val writer = OutputStreamWriter( it )
            template.process(model, writer)
        }
    }
}


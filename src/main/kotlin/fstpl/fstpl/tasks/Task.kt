package fstpl.fstpl.tasks

import com.beust.klaxon.JsonObject
import freemarker.template.Template
import fstpl.fstpl.TemplateResolver
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.file.Path


sealed class Task(
    protected val model: JsonObject,
    protected val file: Path,
    protected val outPath: Path,
    protected val call: String,
    protected val resolve: Boolean
) {

    abstract fun execute(): List<Task>

    protected fun resolve(file: Path, outPath: Path) {
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


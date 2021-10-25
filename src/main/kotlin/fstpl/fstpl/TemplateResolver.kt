package fstpl.fstpl

import com.beust.klaxon.JsonObject
import freemarker.cache.FileTemplateLoader
import freemarker.cache.TemplateLoader
import freemarker.template.Configuration
import fstpl.fstpl.tasks.*
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.security.AccessController
import java.security.PrivilegedActionException
import java.security.PrivilegedExceptionAction
import java.util.*
import kotlin.io.path.name

class MyLoader() : TemplateLoader by FileTemplateLoader() {
    override fun findTemplateSource(name: String?): Any? {
        return File(name!!)
    }
}

class TemplateResolver(private val model: JsonObject, private val tplRoot: Path, private val outRoot: Path) {
    private val tasks: Queue<Task> = LinkedList()

    companion object {
        val engine: Configuration = Configuration()
        init {
            engine.templateLoader = MyLoader()
        }
    }

    fun resolve() {
        walkFirstLevel(tplRoot)
    }

    private fun walkFirstLevel(file: Path) {
        Files.walk(file, 1).use { paths ->
            paths.forEach(this::processSingle)
        }
    }

    private fun processSingle(path: Path) {
        tasks += path.parseTask(model, outRoot)
    }



}

fun Path.parseTask(model: JsonObject, outPath: Path) : Task {
    val name = this.name
    return when {
        name.startsWith("~if") -> If(model, this, outPath)
        name.startsWith("~for") -> Loop(model, this, outPath)
        name.startsWith("~rename") -> Rename(model, this, outPath)
        name.startsWith("~resolve") -> Resolve(model, this, outPath)
        else -> Copy(model, this, outPath)
    }
}
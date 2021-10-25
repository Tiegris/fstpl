package fstpl.fstpl

import com.beust.klaxon.JsonObject
import freemarker.template.Configuration
import fstpl.fstpl.tasks.Copy
import fstpl.fstpl.tasks.If
import fstpl.fstpl.tasks.Loop
import fstpl.fstpl.tasks.Task
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.io.path.name

class TemplateResolver(private val model: JsonObject, private val tplRoot: Path, private val outRoot: Path) {
    private val engine: Configuration = Configuration()
    private val tasks: Queue<Task> = LinkedList()

    init {
        engine.setDirectoryForTemplateLoading(tplRoot.toFile())
    }

    fun resolve() {
        walkSingleLevel(tplRoot)
    }

    private fun walkSingleLevel(file: Path) {
        Files.walk(file, 1).use { paths ->
            paths.forEach(this::processSingle)
        }
    }

    private fun processSingle(path: Path) {
        val name = path.name

        val task = when {
            name.startsWith("~if") -> If(model, path)
            name.startsWith("~for") -> Loop(model, path)
            else -> Copy(model, path)
        }

    }



}

fun Path.parseTask(model: JsonObject) : Task {
    val name = this.name
    return when {
        name.startsWith("~if") -> If(model, this)
        name.startsWith("~for") -> Loop(model, this)
        name.startsWith()
        else -> Copy(model, this)
    }
}
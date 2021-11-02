package fstpl.fstpl

import com.beust.klaxon.JsonObject
import freemarker.template.Configuration
import fstpl.fstpl.tasks.Task
import fstpl.util.extensions.sortTask
import fstpl.util.mkdir
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.streams.toList

object TemplateResolver {
    val engine: Configuration = Configuration()
    lateinit var tplRoot: Path

    fun resolve(model: JsonObject, tplRoot: Path, outRoot: Path) {
        this.tplRoot = tplRoot
        engine.setDirectoryForTemplateLoading(this.tplRoot.toFile())
        val tasks: Queue<Task> = LinkedList()

        tasks += Files.list(this.tplRoot).map { x -> x.sortTask(model, outRoot) }.toList()
        mkdir(outRoot)

        while (tasks.isNotEmpty()) {
            tasks += tasks.poll().execute()
        }
    }
}

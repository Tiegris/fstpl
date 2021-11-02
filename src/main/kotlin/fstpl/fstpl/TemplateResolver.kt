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

class TemplateResolver(private val model: JsonObject, private val tplRoot: Path, private val outRoot: Path) {
    private val tasks: Queue<Task> = LinkedList()

    companion object {
        val engine: Configuration = Configuration()
        lateinit var tplRoot: Path
    }

    init {
        engine.setDirectoryForTemplateLoading(tplRoot.toFile())
        Companion.tplRoot = tplRoot
    }

    fun resolve() {
        tasks += Files.list(tplRoot).map { x -> x.sortTask(model, outRoot) }.toList()
        mkdir(outRoot)

        while (tasks.isNotEmpty()) {
            tasks += tasks.poll().execute()
        }
    }


}

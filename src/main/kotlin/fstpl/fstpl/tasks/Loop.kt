package fstpl.fstpl.tasks

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import fstpl.util.FstplModelException
import fstpl.util.extensions.*
import java.nio.file.Path

class Loop(model: JsonObject, file: Path, outPath: Path, call: String, resolve: Boolean) : Task(model, file, outPath, call, resolve) {

    override fun execute(): List<Task> {
        val key = file.loopKey()
        val elements = model.read(key) as? JsonArray<*> ?: throw FstplModelException("Model element not an array.")

        val tasks = mutableListOf<Task>()

        for (i in elements) {
            if (i is JsonObject) {
                val name = i.readName(call)
                tasks += Copy(i, file, outPath, name, resolve)
            } else {
                throw FstplModelException("Loop element is not a valid key.")
            }
        }

        return tasks
    }

}
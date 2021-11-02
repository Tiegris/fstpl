package fstpl.fstpl.tasks

import com.beust.klaxon.JsonObject
import fstpl.util.cp
import fstpl.util.extensions.*
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.name
import kotlin.streams.toList

class Rename(model: JsonObject, file: Path, outPath: Path, call: String, resolve: Boolean) : Task(model, file, outPath, call, resolve) {

    override fun execute(): List<Task> {
        val name = model.readName(call)
        return listOf(Copy(model, file, outPath, name, resolve))
    }

}
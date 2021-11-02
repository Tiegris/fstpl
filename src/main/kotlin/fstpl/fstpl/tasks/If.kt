package fstpl.fstpl.tasks

import com.beust.klaxon.JsonObject
import fstpl.util.FstplModelException
import fstpl.util.extensions.ifKey
import fstpl.util.extensions.read
import fstpl.util.extensions.readName
import java.nio.file.Path

class If(model: JsonObject, file: Path, outPath: Path, call: String, resolve: Boolean) :
    Task(model, file, outPath, call, resolve) {

    override fun execute(): List<Task> {
        val key = file.ifKey()
        val value =
            model.read(key) as? Boolean ?: throw FstplModelException("Model element not found or not a bool value.")

        val name = model.readName(call)
        return if (value)
            listOf(Copy(model, file, outPath, name, resolve))
        else
            listOf()
    }

}
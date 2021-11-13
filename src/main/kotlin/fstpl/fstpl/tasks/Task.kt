package fstpl.fstpl.tasks

import com.beust.klaxon.JsonObject
import java.nio.file.Path


sealed class Task(
    protected val model: JsonObject,
    protected val file: Path,
    protected val outPath: Path,
    protected val call: String,
    protected val resolve: Boolean
) {

    abstract fun execute(): List<Task>

}


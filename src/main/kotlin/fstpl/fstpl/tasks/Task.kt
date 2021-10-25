package fstpl.fstpl.tasks

import com.beust.klaxon.JsonObject
import java.nio.file.Path

sealed class Task(val model: JsonObject, val file: Path) {
    abstract fun execute()

    abstract fun collectSubTasks(): List <Task>
}



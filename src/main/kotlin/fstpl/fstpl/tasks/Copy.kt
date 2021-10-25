package fstpl.fstpl.tasks

import com.beust.klaxon.JsonObject
import java.nio.file.Files
import java.nio.file.Path

//The most basic Task, it copies the file/folder
class Copy(model: JsonObject, file: Path) : Task(model, file) {
    override fun execute() {
        TODO("Not yet implemented")
    }

    override fun collectSubTasks(): List<Task> {
        val children = Files.list(file)

    }
}
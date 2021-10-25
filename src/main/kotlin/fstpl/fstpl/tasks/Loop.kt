package fstpl.fstpl.tasks

import com.beust.klaxon.JsonObject
import java.nio.file.Path

class Loop(model: JsonObject, file: Path, outPath: Path) : Task(model, file, outPath) {
    override fun resolveSelf() {
        TODO("Not yet implemented")
    }

    override fun collectSubTasks(): List<Task> {
        TODO("Not yet implemented")
    }
}
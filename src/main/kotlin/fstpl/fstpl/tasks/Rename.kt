package fstpl.fstpl.tasks

import com.beust.klaxon.JsonObject
import java.nio.file.Path

class Rename(model: JsonObject, file: Path, outPath: Path) : Task(model, file, outPath) {
    override fun resolveSelf() {
        TODO("Not yet implemented")
    }


}
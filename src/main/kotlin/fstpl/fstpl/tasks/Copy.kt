package fstpl.fstpl.tasks

import com.beust.klaxon.JsonObject
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

//The most basic Task, it copies the file/folder
class Copy(model: JsonObject, file: Path, outPath: Path) : Task(model, file, outPath) {

    override fun resolveSelf() {
        Files.copy(file, outBase, StandardCopyOption.REPLACE_EXISTING)
    }

}
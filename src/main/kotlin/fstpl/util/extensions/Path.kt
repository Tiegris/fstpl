package fstpl.util.extensions

import com.beust.klaxon.JsonObject
import fstpl.fstpl.tasks.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.name

fun Path.isEmpty(): Boolean {
    if (Files.isDirectory(this)) {
        Files.newDirectoryStream(this).use { directory -> return !directory.iterator().hasNext() }
    }
    return false
}

fun Path.sortTask(model: JsonObject, outPath: Path): Task {
    val name = this.name
    return when {
        name.startsWith("~if_") -> If(model, this, outPath, this.caller(), false)
        name.startsWith("~loop_") -> Loop(model, this, outPath, this.caller(), false)
        name.startsWith("~rename_") -> Rename(model, this, outPath, this.caller(), false)
        name.startsWith("~~if_") -> If(model, this, outPath, this.caller(), true)
        name.startsWith("~~loop_") -> Loop(model, this, outPath, this.caller(), true)
        name.startsWith("~~rename_") -> Rename(model, this, outPath, this.caller(), true)
        name.startsWith("~~") -> Copy(model, this, outPath, name.drop(2), true)
        else -> Copy(model, this, outPath, name, false)
    }
}

fun Path.sub(file: String): Path {
    return Paths.get(this.toString(), file)
}

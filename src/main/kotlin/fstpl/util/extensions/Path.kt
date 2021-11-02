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

fun Path.sub(file: String): Path {
    return Paths.get(this.toString(), file)
}

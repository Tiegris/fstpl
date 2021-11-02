package fstpl.util.extensions

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun Path.isEmpty(): Boolean {
    if (Files.isDirectory(this)) {
        Files.newDirectoryStream(this).use { directory -> return !directory.iterator().hasNext() }
    }
    return false
}

fun Path.sub(file: String): Path {
    return Paths.get(this.toString(), file)
}

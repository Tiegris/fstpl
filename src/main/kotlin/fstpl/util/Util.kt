package fstpl.util

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

fun mkdir(folder: Path) {
    if (!folder.exists()) {
        Files.createDirectories(folder)
    } else {
        if (!folder.isDirectory()) {
            throw FstplIOException("Can not make directory, there is a regular file with that name!")
        }
    }
}

fun rmdir(folder: Path) {
    if (folder.exists()) {
        if (!folder.isDirectory()) {
            throw FstplIOException("Can not remove directory, not a Directory!")
        }
        Files.createDirectories(folder)
    }
}

fun cp(from: Path, to: Path) {
    Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING)
}

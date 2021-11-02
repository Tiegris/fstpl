package fstpl.fstpl.tasks

import com.beust.klaxon.JsonObject
import fstpl.util.cp
import fstpl.util.extensions.sortTask
import fstpl.util.extensions.sub
import fstpl.util.mkdir
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.streams.toList


class Copy(model: JsonObject, file: Path, outPath: Path, call: String, resolve: Boolean) : Task(model, file, outPath, call, resolve) {

    override fun execute(): List<Task> {
        return if (file.isDirectory()) executeDirectory() else executeFile()
    }

    private fun executeDirectory(): List<Task> {
        mkdir(outPath.sub(call))
        return Files.list(file).map { x -> x.sortTask(model, outPath.sub(call)) }.toList()
    }

    private fun executeFile(): List<Task> {
        if (resolve)
            resolve(file, outPath.sub(call))
        else
            cp(file, outPath.sub(call))

        return listOf()
    }

}
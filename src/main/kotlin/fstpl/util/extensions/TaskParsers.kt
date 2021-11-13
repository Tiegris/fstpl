package fstpl.util.extensions

import com.beust.klaxon.JsonObject
import fstpl.fstpl.tasks.*
import java.nio.file.Path
import kotlin.io.path.name

const val CALL = "_call_"

fun Path.sortTask(model: JsonObject, outPath: Path): Task {
    val name = this.name
    return when {
        name.startsWith("~if_") -> If(model, this, outPath, this.caller, false)
        name.startsWith("~loop_") -> Loop(model, this, outPath, this.caller, false)
        name.startsWith("~rename_") -> Rename(model, this, outPath, this.caller, false)
        name.startsWith("~~if_") -> If(model, this, outPath, this.caller, true)
        name.startsWith("~~loop_") -> Loop(model, this, outPath, this.caller, true)
        name.startsWith("~~rename_") -> Rename(model, this, outPath, this.caller, true)
        name.startsWith("~~") -> Copy(model, this, outPath, name.drop(2), true)
        else -> Copy(model, this, outPath, name, false)
    }
}

val Path.caller: String
    get() {
        return this.name.getByKeyword(CALL)
    }

val Path.loopKey: String
    get() {
        return this.name.getByKeyword("~loop_").dropCall()
    }

val Path.ifKey: String
    get() {
        return this.name.getByKeyword("~if_").dropCall()
    }

private fun String.dropCall(): String {
    val pos = this.indexOf(CALL)
    return this.dropLast(this.length - pos)
}

private fun String.getByKeyword(keyword: String): String {
    val pos = this.indexOf(keyword)
    return this.takeLast(this.length - pos - keyword.length)
}

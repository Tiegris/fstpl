package fstpl.util.extensions

import com.beust.klaxon.JsonObject
import fstpl.fstpl.tasks.*
import java.nio.file.Path
import kotlin.io.path.name

const val call = "_call_"

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

fun Path.caller(): String {
     return this.getByKeyword(call)
}

fun Path.loopKey(): String {
    return this.getByKeyword("~loop_").dropCall()
}

fun Path.ifKey(): String {
    return this.getByKeyword("~if").dropCall()
}

private fun String.dropCall(): String {
    val pos = this.indexOf(call)
    return this.dropLast(this.length - pos)
}

private fun Path.getByKeyword(keyword: String): String {
    val name = this.name
    val pos = name.indexOf(keyword)
    return name.takeLast(name.length - pos - keyword.length)
}
